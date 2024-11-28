package org.jiang.tools.arithmetic.hash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;
import lombok.Getter;
import org.jiang.tools.exception.BadArgumentException;

/**
 * 一致性哈希算法
 * <br> 这个类只是一个操作哈希环的工具类，本身并不存储于哈希环以及节点的相关状态
 * <br> 修改 consistentHashLoop 的内容会立刻生效，但无法获取修改后影响的点位
 * <br> 建议通过 ConsistentHash 的 putNode、removeNode 来修改
 *
 * @author Bin
 * @since 1.1.5
 */
@Getter
public class ConsistentHash {

    private final ConsistentHashLoop consistentHashLoop;

    private ConsistentHash(ConsistentHashLoop consistentHashLoop) {
        this.consistentHashLoop = consistentHashLoop;
    }

    public static ConsistentHash of(ConsistentHashLoop consistentHashLoop) {
        return new ConsistentHash(consistentHashLoop);
    }

    /**
     * 查找节点,顺时针方向
     * <br> 会调用对象的 hashCode 方法来进行后续的点位计算
     * <br> 建议重写 hashCode 方法来保证在运行多实例情况下相同内容的对象哈希码也相同
     *
     * @param obj 对象
     * @return 节点信息
     */
    public ConsistentHashNode getNode(Object obj) {
        return this.getNode(consistentHashLoop.hashPoint(obj));
    }

    /**
     * 查找节点,顺时针方向
     *
     * @param point 点
     * @return 节点信息
     */
    public ConsistentHashNode getNode(Integer point) {
        TreeMap<Integer, ConsistentHashNode> nodes = this.consistentHashLoop.getNodes();
        if (nodes.isEmpty()) {
            return null;
        }
        Map.Entry<Integer, ConsistentHashNode> entry = nodes.ceilingEntry(point);
        return entry != null ? entry.getValue() : nodes.firstEntry().getValue();
    }

    /**
     * 新增节点
     *
     * @param node 节点信息
     * @return 影响点范围
     */
    public PointScope[] putNode(ConsistentHashNode node) {
        if (node.getPoint() == null) {
            throw new BadArgumentException("node point can't be null");
        }

        // 添加节点到哈希环
        this.consistentHashLoop.addNode(node);

        TreeSet<Integer> points = new TreeSet<>();
        points.add(node.getPoint());
        points.addAll(Arrays.asList(node.getVirtualPoints()));

        // 如果新增是否为第一批节点，则不计算影响范围
        if (this.consistentHashLoop.getNodes().size() == points.size()) {
            return new PointScope[0];
        }

        // 计算出所有新增节点影响到的范围
        TreeMap<Integer, PointScope> pointScopeMap = new TreeMap<>();
        for (Integer point : points) {
            PointScope pointScope = getPointScope(point);
            if (pointScope != null) {
                pointScopeMap.put(point, pointScope);
            }
        }

        return pointScopeMap.values().toArray(new PointScope[0]);
    }

    /**
     * 删除节点
     *
     * @param node 节点
     * @return 影响点范围
     */
    public PointScope[] removeNode(ConsistentHashNode node) {
        return this.removeNode(node.getPoint());
    }

    /**
     * 删除节点
     *
     * @param point 节点所在点
     * @return 影响点范围
     */
    public PointScope[] removeNode(int point) {
        TreeMap<Integer, ConsistentHashNode> nodes = this.consistentHashLoop.getNodes();
        ConsistentHashNode node = nodes.get(point);
        if (node == null) {
            return null;
        }

        // 删除真实节点列表中的节点
        this.consistentHashLoop.getRealNodes().remove(node);

        // 查出所有需要删除的点并删除
        TreeSet<Integer> list = new TreeSet<>();
        list.add(node.getPoint());
        list.addAll(Arrays.asList(node.getVirtualPoints()));
        list.forEach(nodes::remove);

        // 计算出所有删除节点影响到的范围
        Map<Integer, PointScope> pointScopeMap = new HashMap<>(list.size());
        Map<Integer, List<Integer>> nextNodeMap = new HashMap<>(list.size());
        for (Integer endPoint : list) {
            PointScope pointScope = getPointScope(endPoint);
            if (pointScope != null) {
                pointScopeMap.put(endPoint, pointScope);
                if (pointScope.getNextNode() != null) {
                    nextNodeMap.computeIfAbsent(pointScope.getNextNodePoint(), (obj) -> new ArrayList<>()).add(endPoint);
                }
            }
        }

        // 如果删除的节点只有一个，则直接返回
        if (pointScopeMap.size() <= 1) {
            return pointScopeMap.values().toArray(new PointScope[0]);
        }

        // 去掉重叠的影响范围
        List<PointScope> result = new ArrayList<>(nextNodeMap.size());

        for (Map.Entry<Integer, List<Integer>> entry : nextNodeMap.entrySet()) {
            Integer nextNodePoint = entry.getKey();
            List<Integer> nodeList = entry.getValue();
            if (nodeList.size() == 1) {
                result.add(pointScopeMap.get(nodeList.get(0)));
                continue;
            }
            Integer longestPoint = geLongestDistance(nodeList, nextNodePoint);
            Integer shortestPoint = geShortestDistance(nodeList, nextNodePoint);
            PointScope pointScope = pointScopeMap.get(longestPoint);
            pointScope.setEnd(shortestPoint);
            result.add(pointScope);
        }

        return result.toArray(new PointScope[0]);
    }

    /**
     * 获取影响点范围
     *
     * @param endNode 截止节点
     * @return 影响点范围
     */
    public PointScope getPointScope(ConsistentHashNode endNode) {
        return getPointScope(endNode.getPoint());
    }

    /**
     * 获取影响点范围
     *
     * @param endPoint 截止点
     * @return 影响点范围
     */
    public PointScope getPointScope(Integer endPoint) {
        TreeMap<Integer, ConsistentHashNode> nodes = this.consistentHashLoop.getNodes();
        if (nodes.isEmpty()) {
            return null;
        }
        Map.Entry<Integer, ConsistentHashNode> entry = nodes.lowerEntry(endPoint);
        Integer startPoint = entry != null ? entry.getKey() : nodes.lastEntry().getKey();
        return getPointScope(startPoint, endPoint);
    }

    /**
     * 获取影响点范围
     *
     * @param startPoint 起始节点
     * @param endPoint   截止节点
     * @return 影响点范围
     */
    public PointScope getPointScope(Integer startPoint, Integer endPoint) {
        if (startPoint.equals(endPoint)) {
            return null;
        }
        // 查询影响范围的下一个节点
        ConsistentHashNode nextNode = null;
        Integer nextNodePoint = null;
        TreeMap<Integer, ConsistentHashNode> nodes = this.consistentHashLoop.getNodes();
        if (!nodes.isEmpty()) {
            Map.Entry<Integer, ConsistentHashNode> entry = nodes.ceilingEntry(endPoint);
            if (entry == null) {
                entry = nodes.firstEntry();
            }
            nextNodePoint = entry.getKey();
            nextNode = entry.getValue();
        }
        // 防止点数超过最大点总数
        Integer pointCount = this.getConsistentHashLoop().getPointCount();
        startPoint = startPoint.equals(pointCount) ? pointCount : startPoint + 1;
        return new PointScope(startPoint, endPoint, nextNode, nextNodePoint, this.consistentHashLoop);
    }

    /**
     * 获取点到目标点的距离 顺时针方向
     *
     * @param point  点
     * @param target 目标点
     * @return 最近距离点
     */
    private int getTargetDistance(int point, int target) {
        if (point <= target) {
            return target - point;
        }
        return this.consistentHashLoop.getPointCount() - target + point;
    }

    /**
     * 获取最距离目标点最长的节点 顺时针方向
     *
     * @param points 点数组
     * @param target 目标点
     * @return 点
     */
    private Integer geLongestDistance(List<Integer> points, int target) {
        Integer result = null;
        int longest = 0;
        for (Integer point : points) {
            int distance = getTargetDistance(point, target);
            if (result == null || distance > longest) {
                result = point;
                longest = distance;
            }
        }
        return result;
    }

    /**
     * 获取最距离目标点最短的点 顺时针方向
     *
     * @param points 点数组
     * @param target 目标点
     * @return 点
     */
    private Integer geShortestDistance(List<Integer> points, int target) {
        Integer result = null;
        int longest = 0;
        for (Integer point : points) {
            int distance = getTargetDistance(point, target);
            if (result == null || distance < longest) {
                result = point;
                longest = distance;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        int start = 0;
        for (Entry<Integer, ConsistentHashNode> entry : this.consistentHashLoop.getNodes().entrySet()) {
            ConsistentHashNode node = entry.getValue();
            sb.append(" ");
            sb.append(start);
            sb.append("...");
            sb.append(entry.getKey());
            sb.append(" = ");
            sb.append(node.getTarget());
            if (!entry.getKey().equals(node.getPoint())) {
                sb.append("(virtual)");
            }
            sb.append(" |");
            start = entry.getKey() + 1;
        }
        if (sb.length() > 1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("]");
        return sb.toString();
    }

}

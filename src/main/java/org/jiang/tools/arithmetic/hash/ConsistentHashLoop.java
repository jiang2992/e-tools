package org.jiang.tools.arithmetic.hash;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;
import lombok.Getter;
import org.jiang.tools.exception.BadArgumentException;
import org.jiang.tools.text.RandomUtils;

/**
 * 一致性哈希环
 * <br> 存储相关状态信息及节点信息，包括真实节点与虚拟节点，但是你并不需要关心任何与虚拟节点相关的数据
 *
 * @author Bin
 * @date 1.1.5
 */
@Getter
public class ConsistentHashLoop implements Serializable {

    /**
     * 槽位总数
     */
    private Integer pointCount;

    /**
     * 环上节点(包含虚拟节点)
     */
    private transient TreeMap<Integer, ConsistentHashNode> nodes;

    /**
     * 真实节点(不包含虚拟节点)
     */
    private List<ConsistentHashNode> realNodes;

    public ConsistentHashLoop() {
        this.init();
    }

    public ConsistentHashLoop(Integer pointCount) {
        this();
        this.pointCount = pointCount;
    }

    private void init() {
        this.nodes = this.nodes != null ? this.nodes : new TreeMap<>();
        this.realNodes = this.realNodes != null ? this.realNodes : new ArrayList<>();
    }

    /**
     * 设置槽位总数，只能被设置一次
     *
     * @param pointCount 槽位总数
     */
    public void setPointCount(Integer pointCount) {
        if (this.pointCount != null) {
            throw new BadArgumentException("point count can not be changed");
        }
        this.pointCount = pointCount;
    }

    /**
     * 设置节点，会覆盖原有数据并初始化
     *
     * @param realNodes 节点列表
     */
    public void setRealNodes(List<ConsistentHashNode> realNodes) {
        this.nodes = new TreeMap<>();
        this.realNodes = new ArrayList<>(realNodes.size());
        for (ConsistentHashNode realNode : realNodes) {
            this.createNode(realNode.getPoint(), realNode.getTarget(), realNode.getVirtualPoints());
        }
    }

    /**
     * 添加节点
     *
     * @param node 节点信息
     * @return 添加的节点总数(包括虚拟节点)
     */
    public int addNode(ConsistentHashNode node) {
        realNodes.add(node);
        nodes.put(node.getPoint(), node);
        if (node.getVirtualPoints() == null) {
            return 1;
        }
        for (Integer virtualPoint : node.getVirtualPoints()) {
            nodes.put(virtualPoint, node);
        }
        return node.getVirtualPoints().length + 1;
    }

    /**
     * 创建节点, 只创建不添加
     * <br> point 将根据 target 来映射
     *
     * @param target 目标
     * @return 节点信息
     */
    public ConsistentHashNode createNode(Object target) {
        return this.createNode(generateNodePoint(target), target, 0);
    }

    /**
     * 创建节点, 只创建不添加
     *
     * @param point  所在点
     * @param target 目标
     * @return 节点信息
     */
    public ConsistentHashNode createNode(Integer point, Object target) {
        return this.createNode(point, target, 0);
    }

    /**
     * 创建节点, 只创建不添加
     * <br> point 将根据 target 来映射
     *
     * @param target       目标
     * @param virtualCount 虚拟节点个数
     * @return 节点信息
     */
    public ConsistentHashNode createNode(Object target, Integer virtualCount) {
        return this.createNode(generateNodePoint(target), target, virtualCount);
    }

    /**
     * 创建节点, 只创建不添加
     *
     * @param point        所在点
     * @param target       目标
     * @param virtualCount 虚拟节点个数
     * @return 节点信息
     */
    public ConsistentHashNode createNode(Integer point, Object target, Integer virtualCount) {
        HashSet<Integer> virtualPoints = new HashSet<>(virtualCount);
        for (int i = 0; i < virtualCount; i++) {
            StringBuilder sb = new StringBuilder(point);
            sb.append("#");
            sb.append(RandomUtils.generate(6));
            int virtualPoint = generateNodePoint(sb);
            if (point == virtualPoint || virtualPoints.contains(virtualPoint)) {
                i--;
                continue;
            }
            virtualPoints.add(virtualPoint);
        }
        return this.createNode(point, target, virtualPoints.toArray(new Integer[0]));
    }

    /**
     * 创建节点, 只创建不添加
     *
     * @param point         所在点
     * @param target        目标
     * @param virtualPoints 虚拟点列表
     * @return 节点信息
     */
    public ConsistentHashNode createNode(Integer point, Object target, Integer[] virtualPoints) {
        if (point == null) {
            throw new BadArgumentException("node point can't be null");
        }
        return new ConsistentHashNode(point, target, virtualPoints);
    }

    private int generateNodePoint(Object obj) {
        int point;
        String offset = null;
        do {
            point = this.hashPoint(obj, offset);
            offset = RandomUtils.generate(6);
        } while (nodes.containsKey(point));
        return point;
    }

    public int hashPoint(Object... objs) {
        if (objs.length == 0) {
            throw new BadArgumentException("objects can't be empty");
        }
        return Math.abs(Objects.hash(objs) % this.getPointCount());
    }

    /**
     * 反序列化事件
     *
     * @param ois 输入流
     * @throws IOException            流输入异常
     * @throws ClassNotFoundException 类未找到异常
     */
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();

        // 在反序列化后，初始化 nodes 变量
        List<ConsistentHashNode> realNodes = this.realNodes;
        this.realNodes = null;
        this.init();
        realNodes.forEach(this::addNode);
    }

}

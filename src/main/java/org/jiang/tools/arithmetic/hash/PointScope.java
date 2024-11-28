package org.jiang.tools.arithmetic.hash;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 一致性哈希点范围
 *
 * @author Bin
 * @date 1.1.5
 */
@Data
@AllArgsConstructor
public class PointScope {

    /**
     * 起始点(包含)
     */
    private Integer start;

    /**
     * 截止点(包含) 当截止点小于起始点时，需包含截止点以下所有点
     */
    private Integer end;

    /**
     * 截止点的下一个节点
     */
    private final ConsistentHashNode nextNode;

    /**
     * 截止点的下一个节点所在点
     * <br> 不一定会与 nextNode 中的 point 一致，在存在虚拟节点的情况下，在哈希环中会有多个不同的点指向同一节点
     */
    private final Integer nextNodePoint;

    /**
     * 所属的哈希环
     */
    private final ConsistentHashLoop loop;

    /**
     * 判断点是否包含在范围中
     *
     * @param point 点
     * @return 是否包含
     */
    public boolean contains(Integer point) {
        if (start <= end) {
            return point >= start && point <= end;
        }
        return point > start || point < end;
    }

    /**
     * 过滤出所有存在范围内的点
     *
     * @param points 点
     * @return 所有存在范围内的点
     */
    public Integer[] filtration(Integer... points) {
        List<Integer> containsList = new ArrayList<>();
        for (Integer point : points) {
            if (this.contains(point)) {
                containsList.add(point);
            }
        }
        return containsList.toArray(new Integer[0]);
    }

    /**
     * 过滤出所有存在范围内的对象
     *
     * @param objs 对象
     * @return 所有存在范围内的对象
     */
    public Object[] filtration(Object... objs) {
        List<Object> containsList = new ArrayList<>();
        for (Object obj : objs) {
            if (this.contains(loop.hashPoint(obj))) {
                containsList.add(obj);
            }
        }
        return containsList.toArray();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append("(start=");
        sb.append(start);
        sb.append(", end=");
        sb.append(end);
        if (nextNode != null) {
            sb.append(", next=");
            sb.append(nextNode.getTarget());
        }
        sb.append(")");
        return sb.toString();
    }

}

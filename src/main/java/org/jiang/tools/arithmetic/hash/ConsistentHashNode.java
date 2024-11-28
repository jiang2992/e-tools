package org.jiang.tools.arithmetic.hash;

import java.io.Serializable;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 一致性哈希节点
 *
 * @author Bin
 * @since 1.1.5
 */
@Getter
@AllArgsConstructor
@ToString
public class ConsistentHashNode implements Serializable {

    /**
     * 所在点
     */
    private final Integer point;

    /**
     * 关联目标
     */
    @Setter
    private Object target;

    /**
     * 虚拟点列表
     */
    private final Integer[] virtualPoints;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ConsistentHashNode that = (ConsistentHashNode) o;
        return Objects.equals(point, that.point) && Objects.equals(target, that.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(point, target);
    }

}

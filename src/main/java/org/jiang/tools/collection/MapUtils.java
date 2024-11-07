package org.jiang.tools.collection;

import java.util.HashMap;

/**
 * Map工具类
 *
 * @author Bin
 * @since 1.1.5
 */
public class MapUtils {

    /**
     * 计算Map的初始容量
     *
     * @param size 元素个数
     * @return 初始容量
     */
    public static int calculateInitialCapacity(int size) {
        return calculateInitialCapacity(size, 0.75f);
    }

    /**
     * 计算Map的初始容量
     *
     * @param size       元素个数
     * @param loadFactor 负载因子
     * @return 初始容量
     */
    public static int calculateInitialCapacity(int size, float loadFactor) {
        return Integer.highestOneBit((int) Math.ceil(size / loadFactor) - 1) << 1;
    }

    /**
     * 创建一个HashMap 该HashMap在存储不超过12个元素的情况下不会进行扩容
     *
     * @return HashMap
     */
    public static HashMap<?, ?> create() {
        return new HashMap<>(16);
    }

    /**
     * 创建一个指定初始元素容量的HashMap 该HashMap在存储不超过 size 个元素的情况下不会进行扩容
     *
     * @param size 初始元素容量
     * @return HashMap
     */
    public static HashMap<?, ?> create(int size) {
        return new HashMap<>(calculateInitialCapacity(size));
    }

}

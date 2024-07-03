package org.jiang.tools.collection;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Map工具类
 *
 * @author Bin
 * @since 2024/6/21 11:47
 */
public class MapUtils {

    public static int calculateInitialCapacity(int size) {
        return calculateInitialCapacity(size, 0.75f);
    }

    public static int calculateInitialCapacity(int size, float loadFactor) {
        return Integer.highestOneBit((int) Math.ceil(size / loadFactor) - 1) << 1;
    }

    public static HashMap<?, ?> create() {
        return new HashMap<>(16);
    }

    public static HashMap<?, ?> create(int size) {
        return new HashMap<>(calculateInitialCapacity(size));
    }

//    public static LinkedHashMap<>

}

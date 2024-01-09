package org.jiang.tools.collection;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Set集合 工具类
 *
 * @author Bin
 * @since 1.0.0
 */
public class SetUtils {

    public static <T> Set<T> asArray(T[] array) {
        if (array == null) {
            return new LinkedHashSet<>(0);
        }
        return new LinkedHashSet<>(Arrays.asList(array));
    }

    public static <T> Set<T> asList(List<T> list) {
        if (list == null || list.isEmpty()) {
            return new LinkedHashSet<>(0);
        }
        return new LinkedHashSet<>(list);
    }

    public static Set<String> asString(String str, String regex) {
        if (str == null || regex == null) {
            return new LinkedHashSet<>(0);
        }
        String[] strings = str.split(regex);
        return SetUtils.asArray(strings);
    }

}

package org.jiang.tools.util;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * List集合工具类
 *
 * @author Bin
 * @date 2020-11-13 16:06
 */
public class ListUtils {

    public static <T> List<T> asSet(Set<T> set) {
        List<T> list = new ArrayList<>(set.size());
        list.addAll(set);
        return list;
    }

    public static <T, R> List<R> convert(List<T> list, Function<T, R> fun) {
        if (list == null) {
            return null;
        }
        return list.stream().map(fun).collect(Collectors.toList());
    }

    public static <T, K> List<T> distinct(List<T> list, Function<T, K> fun) {
        if (list == null) {
            return null;
        }
        Map<K, T> map = new HashMap<>(list.size());
        List<T> result = new ArrayList<>(list.size());
        for (T t : list) {
            K key = fun.apply(t);
            if (map.containsKey(key)) {
                continue;
            }
            map.put(key, t);
            result.add(t);
        }
        return result;
    }

}

package org.jiang.tools.collection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * List集合工具类
 *
 * @author Bin
 * @since 1.0.0
 */
public class ListUtils {

    /**
     * Set转ArrayList
     *
     * @param set set
     * @param <T> 元素类型
     * @return ArrayList
     */
    public static <T> List<T> asSet(Set<T> set) {
        List<T> list = new ArrayList<>(set.size());
        list.addAll(set);
        return list;
    }

    /**
     * 将列表中的元素转换成另一对象
     *
     * @param list 元素列表
     * @param fun  转换函数
     * @param <T>  元素原类型
     * @param <R>  转换后的元素类型
     * @return 转换后的列表
     */
    public static <T, R> List<R> convert(List<T> list, Function<T, R> fun) {
        if (list == null) {
            return null;
        }
        return list.stream().map(fun).collect(Collectors.toList());
    }

    /**
     * 集合去重
     *
     * @param list 元素集合
     * @param fun  去重属性函数
     * @param <T>  元素类型
     * @param <K>  去重属性的类型
     * @return 去重后的集合
     */
    public static <T, K> List<T> distinct(List<T> list, Function<T, K> fun) {
        if (list == null) {
            return null;
        }
        Set<K> keys = new HashSet<>(list.size());
        List<T> result = new ArrayList<>(list.size());
        for (T t : list) {
            K key = fun.apply(t);
            if (keys.contains(key)) {
                continue;
            }
            keys.add(key);
            result.add(t);
        }
        return result;
    }

}

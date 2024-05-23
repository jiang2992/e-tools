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
public class SetUtils extends CollectionUtils {

    /**
     * 将数组转为LinkedHashSet
     *
     * @param array 数组
     * @param <T>   元素类型
     * @return Set集合
     */
    public static <T> Set<T> asArray(T[] array) {
        if (array == null) {
            return new LinkedHashSet<>(0);
        }
        return new LinkedHashSet<>(Arrays.asList(array));
    }

    /**
     * 将List转为LinkedHashSet
     *
     * @param list List对象
     * @param <T>  元素类型
     * @return Set集合
     */
    public static <T> Set<T> asList(List<T> list) {
        if (list == null || list.isEmpty()) {
            return new LinkedHashSet<>(0);
        }
        return new LinkedHashSet<>(list);
    }

    /**
     * 切割字符串并返回Set
     *
     * @param str   字符串
     * @param regex 切割符
     * @return 切割后的Set集合
     */
    public static Set<String> asString(String str, String regex) {
        if (str == null || regex == null) {
            return new LinkedHashSet<>(0);
        }
        String[] strings = str.split(regex);
        return SetUtils.asArray(strings);
    }

}

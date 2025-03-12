package org.jiang.tools.object;

import java.lang.reflect.Array;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.jiang.tools.collection.map.BiMap;
import org.jiang.tools.exception.BadArgumentException;

/**
 * 类型转换相关工具类
 *
 * @author Bin
 * @since 1.1.6
 */
public class ConvertUtils {

    private static final BiMap<Class<?>, Class<?>> PRIMITIVE_WRAP_TYPE_MAP = new BiMap<>();

    static {
        PRIMITIVE_WRAP_TYPE_MAP.put(Boolean.class, boolean.class);
        PRIMITIVE_WRAP_TYPE_MAP.put(Character.class, char.class);
        PRIMITIVE_WRAP_TYPE_MAP.put(Byte.class, byte.class);
        PRIMITIVE_WRAP_TYPE_MAP.put(Short.class, short.class);
        PRIMITIVE_WRAP_TYPE_MAP.put(Integer.class, int.class);
        PRIMITIVE_WRAP_TYPE_MAP.put(Long.class, long.class);
        PRIMITIVE_WRAP_TYPE_MAP.put(Float.class, float.class);
        PRIMITIVE_WRAP_TYPE_MAP.put(Double.class, double.class);
    }

    /**
     * 获取包装类型的基础数据类型，如果非包装类型，则直接返回
     *
     * @param type 包装类型
     * @return 基础数据类型
     */
    public static Class<?> getPrimitiveType(Class<?> type) {
        Class<?> baseType = PRIMITIVE_WRAP_TYPE_MAP.getByKey(type);
        return baseType != null ? baseType : type;
    }

    /**
     * 获取基础数据类型的包装类型，如果非基础数据类型，则直接返回
     *
     * @param type 基础数据类型
     * @return 包装类型
     */
    public static Class<?> getWrapType(Class<?> type) {
        Class<?> wrapType = PRIMITIVE_WRAP_TYPE_MAP.getByValue(type);
        return wrapType != null ? wrapType : type;
    }

    /**
     * 对比两个类型，对于基础类型会自动拆装包
     *
     * @param a 类型A
     * @param b 类型B
     * @return 是否一致
     */
    public static boolean typeEquals(Class<?> a, Class<?> b) {
        if (a == null) {
            return b == null;
        }
        if (a.equals(b)) {
            return true;
        }
        Class<?> type = PRIMITIVE_WRAP_TYPE_MAP.getByKey(a);
        if (type != null) {
            return type.equals(b);
        }
        type = PRIMITIVE_WRAP_TYPE_MAP.getByKey(b);
        if (type != null) {
            return type.equals(a);
        }
        return false;
    }

    /**
     * 将值转换为目标类型
     * <br> 支持数组与集合的相互转换
     *
     * @param value      值
     * @param targetType 目标类型
     * @param <T>        目标类型泛型
     * @return 转换后的值
     */
    @SuppressWarnings("unchecked")
    public static <T> T to(Object value, Class<T> targetType) {
        if (value == null) {
            return null;
        }
        if (targetType.isAssignableFrom(value.getClass())) {
            return (T) value;
        }
        if (typeEquals(value.getClass(), targetType)) {
            return (T) value;
        }
        if (targetType.equals(String.class)) {
            return (T) value.toString();
        }

        Stream<Object> stream = null;

        if (value.getClass().isArray()) {
            stream = Arrays.stream((Object[]) value);
        } else if (Iterable.class.isAssignableFrom(value.getClass())) {
            stream = StreamSupport.stream(((Iterable<Object>) value).spliterator(), false);
        }

        if (stream == null) {
            throw new BadArgumentException(
                    String.format("Type cannot be converted (%s to %s)", value.getClass().getSimpleName(), targetType.getSimpleName()));
        }

        if (Collection.class.isAssignableFrom(targetType)) {
            Collection<Object> collection;
            if (Modifier.isInterface(targetType.getModifiers()) || Modifier.isAbstract(targetType.getModifiers())) {
                if (Set.class.isAssignableFrom(targetType)) {
                    collection = new HashSet<>();
                } else {
                    collection = new ArrayList<>();
                }
            } else {
                try {
                    collection = (Collection<Object>) targetType.newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            stream.forEach(collection::add);
            return (T) collection;
        } else if (targetType.isArray()) {
            return (T) stream.toArray(size -> (Object[]) Array.newInstance(targetType.getComponentType(), size));
        }

        throw new BadArgumentException(
                String.format("Type cannot be converted (%s to %s)", value.getClass().getSimpleName(), targetType.getSimpleName()));
    }

}

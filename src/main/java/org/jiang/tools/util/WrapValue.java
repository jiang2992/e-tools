package org.jiang.tools.util;

/**
 * 值包装工具类
 *
 * @author Bin
 * @date 2020-11-02 22:01
 */
public class WrapValue<T> {

    private T value;

    private WrapValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public static <T> WrapValue<T> of(T t) {
        return new WrapValue<>(t);
    }

    public String toString() {
        return String.valueOf(value);
    }

}

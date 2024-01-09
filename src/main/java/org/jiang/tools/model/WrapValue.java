package org.jiang.tools.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 值包装工具类
 *
 * @author Bin
 * @since 1.0.0
 */
@Setter
@Getter
public class WrapValue<T> implements Serializable {

    private T value;

    private WrapValue(T value) {
        this.value = value;
    }

    public static <T> WrapValue<T> of(T t) {
        return new WrapValue<>(t);
    }

    public String toString() {
        return String.valueOf(value);
    }

}

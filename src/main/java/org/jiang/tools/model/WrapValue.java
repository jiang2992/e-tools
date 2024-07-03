package org.jiang.tools.model;

import java.io.Serializable;

/**
 * 值包装工具类
 *
 * @author Bin
 * @since 1.0.0
 */
public class WrapValue<T> implements Serializable {

    private volatile T value;

    private WrapValue(T value) {
        this.value = value;
    }

    public static <T> WrapValue<T> of(T t) {
        return new WrapValue<>(t);
    }

    public T get(){
        return value;
    }

    public T getOrDefault(T defaultValue){
        T result = get();
        if(result == null) {
            result = defaultValue;
        }
        return result;
    }

    public void set(T value){
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

}

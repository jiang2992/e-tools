package org.jiang.tools.test;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import org.jiang.tools.collection.CollectionUtils;
import org.jiang.tools.text.StringUtils;

/**
 * 断言工具类
 *
 * @author Bin
 * @since 1.0.0
 */
public class Assert {

    /**
     * 非空
     *
     * @param obj 断言对象
     * @param <T> 对象类型
     * @return 断言结果
     */
    public static <T> AssertResult<T> notNull(T obj) {
        return AssertResult.build(obj, obj != null);
    }

    /**
     * 为空
     *
     * @param obj 断言对象
     * @param <T> 对象类型
     * @return 断言结果
     */
    public static <T> AssertResult<T> isNull(T obj) {
        return AssertResult.build(obj, obj == null);
    }

    /**
     * 字符串不为空，并且长度大于0
     *
     * @param obj 字符串断言对象
     * @param <T> 对象类型
     * @return 断言结果
     */
    public static <T extends CharSequence> AssertResult<T> notEmpty(T obj) {
        return AssertResult.build(obj, StringUtils.notEmpty(obj));
    }

    /**
     * 字符串为空或长度为0
     *
     * @param obj 字符串断言对象
     * @param <T> 对象类型
     * @return 断言结果
     */
    public static <T extends CharSequence> AssertResult<T> isEmpty(T obj) {
        return AssertResult.build(obj, StringUtils.isEmpty(obj));
    }

    /**
     * 集合不为空，并且元素个数大于0
     *
     * @param obj 集合断言对象
     * @param <T> 对象类型
     * @return 断言结果
     */
    public static <T extends Collection<?>> AssertResult<T> notEmpty(T obj) {
        return AssertResult.build(obj, CollectionUtils.notEmpty(obj));
    }

    /**
     * 集合为空或元素个数等于0
     *
     * @param obj 集合断言对象
     * @param <T> 对象类型
     * @return 断言结果
     */
    public static <T extends Collection<?>> AssertResult<T> isEmpty(T obj) {
        return AssertResult.build(obj, CollectionUtils.isEmpty(obj));
    }

    /**
     * 两个对象相等
     * 通过对象的equals方法判断，当两个对象都为null时，结果为true，返回断言对象
     *
     * @param obj    断言对象
     * @param target 目标对象
     * @param <T>    对象类型
     * @return 断言结果
     */
    public static <T> AssertResult<T> equals(T obj, T target) {
        if (obj == null) {
            return AssertResult.build(null, target == null);
        }
        return AssertResult.build(obj, obj.equals(target));
    }

    /**
     * 两个对象不相等
     * 通过对象的equals方法判断，当两个对象都为null时，结果为false，返回断言对象
     *
     * @param obj    断言对象
     * @param target 目标对象
     * @param <T>    对象类型
     * @return 断言结果
     */
    public static <T> AssertResult<T> notEquals(T obj, T target) {
        if (obj == null) {
            return AssertResult.build(null, target != null);
        }
        return AssertResult.build(obj, !obj.equals(target));
    }

    /**
     * 自定义断言结果
     *
     * @param isTrue 断言结果
     * @return 断言结果
     */
    public static AssertResult<Boolean> apply(boolean isTrue) {
        return AssertResult.build(isTrue, isTrue);
    }

    /**
     * 自定义断言结果
     *
     * @param obj    断言对象
     * @param isTrue 断言结果
     * @param <T>    对象类型
     * @return 断言结果
     */
    public static <T> AssertResult<T> apply(T obj, boolean isTrue) {
        return AssertResult.build(obj, isTrue);
    }

    /**
     * 自定义断言函数
     *
     * @param obj      断言对象
     * @param function 断言函数
     * @param <T>      对象类型
     * @return 断言结果
     */
    public static <T> AssertResult<T> apply(T obj, Function<T, Boolean> function) {
        return AssertResult.build(obj, function.apply(obj));
    }

    /**
     * 断言结果
     *
     * @param <T> 对象类型
     */
    public static class AssertResult<T> {

        private T obj;
        private boolean isTrue;

        public static <T> AssertResult<T> build(T t, boolean isTrue) {
            AssertResult<T> assertResult = new AssertResult<>();
            assertResult.obj = t;
            assertResult.isTrue = isTrue;
            return assertResult;
        }

        /**
         * 为true时返回断言对象，否则返回目标对象
         *
         * @param t 目标对象
         * @return 断言对象或目标对象
         */
        public T or(T t) {
            if (isTrue) {
                return obj;
            }
            return t;
        }

        /**
         * 为true时返回断言对象，否则调用处理函数并返回函数结果
         *
         * @param supplier 处理函数
         * @return 断言对象或函数结果
         */
        public T or(Supplier<T> supplier) {
            if (isTrue) {
                return obj;
            }
            return supplier.get();
        }

        /**
         * 为true时返回断言对象，否则抛出异常对象
         *
         * @param e 异常对象
         * @return 断言对象
         * @throws RuntimeException 异常对象
         */
        public T orThrow(RuntimeException e) throws RuntimeException {
            if (isTrue) {
                return obj;
            }
            throw e;
        }

        /**
         * 为true时返回断言对象，否则调用处理函数并抛出函数返回的异常
         *
         * @param supplier 处理函数
         * @return 断言对象
         * @throws RuntimeException 函数返回的异常
         */
        public T orThrow(Supplier<RuntimeException> supplier) throws RuntimeException {
            if (isTrue) {
                return obj;
            }
            throw supplier.get();
        }

        /**
         * 为true时返回断言对象，否则调用处理函数并抛出函数返回的异常
         * 该方法接收额外一个异常信息参数message，会被作为参数传到处理函数中
         * 建议用法：AssertResult.orThrow(RuntimeException::new,"error message")
         *
         * @param function 处理函数
         * @param message  异常信息
         * @return 断言对象
         * @throws RuntimeException 函数返回的异常
         */
        public T orThrow(Function<String, RuntimeException> function, String message) throws RuntimeException {
            if (isTrue) {
                return obj;
            }
            throw function.apply(message);
        }

        public boolean isTrue() {
            return this.isTrue;
        }

        /**
         * 为true时执行处理函数
         *
         * @param consumer 处理函数
         * @return 当前断言结果
         */
        public AssertResult<T> isTrue(Consumer<T> consumer) {
            if (!isTrue) {
                return this;
            }
            consumer.accept(obj);
            return this;
        }

    }

}

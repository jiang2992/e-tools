package org.jiang.tools.test;

import java.util.function.Supplier;

/**
 * 断言工具类
 *
 * @author Bin
 * @since 1.0.0
 */
public class Assert {

    public static <T> AssertResult<T> nonNull(T obj) {
        return AssertResult.build(obj, obj != null);
    }

    public static <T extends CharSequence> AssertResult<T> nonEmpty(T cs) {
        return AssertResult.build(cs, cs != null && cs.length() > 0);
    }

    /**
     * 断言结果
     *
     * @param <T>
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

        public T or(T t) {
            if (isTrue) {
                return obj;
            }
            return t;
        }

        public T or(Supplier<T> supplier) {
            if (isTrue) {
                return obj;
            }
            return supplier.get();
        }

        public T orThrows(RuntimeException e) {
            if (isTrue) {
                return obj;
            }
            throw e;
        }

        public T orThrows(Supplier<RuntimeException> supplier) {
            if (isTrue) {
                return obj;
            }
            throw supplier.get();
        }

    }

}

package org.jiang.tools.decorator;

/**
 * 代码段函数接口
 *
 * @author Bin
 * @since 1.1.9
 */
@FunctionalInterface
public interface ResultfulCodeSegment<T> {

    /**
     * 执行
     */
    T exec();

}

package org.jiang.tools.decorator;

/**
 * 装饰器基类
 *
 * @author Bin
 * @since 1.0.0
 */
public abstract class BaseDecorator<T> {

    protected final CodeSegment codeSegment;

    public BaseDecorator(CodeSegment codeSegment) {
        this.codeSegment = codeSegment;
    }

    /**
     * 运行装饰后的代码段
     *
     * @return 运行结果
     */
    public abstract T run();

}

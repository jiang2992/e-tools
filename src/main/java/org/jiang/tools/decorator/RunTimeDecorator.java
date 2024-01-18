package org.jiang.tools.decorator;

import java.util.function.Consumer;

/**
 * 运行时间装饰器
 *
 * @author Bin
 * @since 1.0.0
 */
public class RunTimeDecorator extends BaseDecorator<Long> {

    public RunTimeDecorator(CodeSegment codeSegment) {
        super(codeSegment);
    }

    @Override
    public Long run() {
        long startTime = System.currentTimeMillis();
        codeSegment.exec();
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    public static Long run(CodeSegment codeSegment) {
        RunTimeDecorator decorator = new RunTimeDecorator(codeSegment);
        return decorator.run();
    }

    public static Long run(CodeSegment codeSegment, Consumer<Long> consumer) {
        RunTimeDecorator decorator = new RunTimeDecorator(codeSegment);
        Long time = decorator.run();
        consumer.accept(time);
        return time;
    }

}

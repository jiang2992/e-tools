package org.jiang.tools.decorator;

/**
 * 运行时间装饰器
 *
 * @author Bin
 * @date 2020/12/29 14:45
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

}

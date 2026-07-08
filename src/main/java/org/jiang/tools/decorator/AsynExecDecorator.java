package org.jiang.tools.decorator;

/**
 * 异步执行装饰器
 *
 * @author Bin
 * @since 1.0.0
 */
public class AsynExecDecorator extends BaseDecorator<Thread> {

    private final CodeSegment codeSegment;

    public AsynExecDecorator(CodeSegment codeSegment) {
        this.codeSegment = codeSegment;
    }

    @Override
    public Thread run() {
        Thread thread = new Thread(codeSegment::exec);
        if (!thread.isAlive()) {
            thread.start();
        }
        return thread;
    }

    public static Thread run(CodeSegment codeSegment) {
        AsynExecDecorator decorator = new AsynExecDecorator(codeSegment);
        return decorator.run();
    }

}

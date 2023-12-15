package org.jiang.tools.decorator;

/**
 * 异步执行装饰器
 *
 * @author Bin
 * @since 1.0.0
 */
public class AsynExecDecorator extends BaseDecorator<Thread> {

    private final Thread thread;

    public AsynExecDecorator(CodeSegment codeSegment) {
        super(codeSegment);
        thread = new Thread(codeSegment::exec);
    }

    @Override
    public Thread run() {
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

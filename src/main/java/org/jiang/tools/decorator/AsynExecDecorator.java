package org.jiang.tools.decorator;

/**
 * 异步执行装饰器
 *
 * @author Bin
 * @date 2023/12/12 17:37
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

}

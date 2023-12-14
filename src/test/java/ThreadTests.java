import org.jiang.tools.decorator.AsynExecDecorator;
import org.jiang.tools.lock.Lock;
import org.jiang.tools.lock.SpinFairLock;
import org.jiang.tools.util.WrapValue;
import org.junit.Test;

/**
 * @author Bin
 * @date 2021/3/17 16:33
 */
public class ThreadTests {

    private int count = 0;
    private final Lock lock = new SpinFairLock();

    public void execute(int index) {
        lock.lock();
        System.out.println("线程INDEX: " + index);
        try {
            Thread.sleep(10);
        } catch (Exception ignore) {
        }
        count++;
        lock.unlock();
    }

    @Test
    public void start() {
        long startTime = System.currentTimeMillis();
        Thread[] ts = new Thread[10];
        for (int i = 0; i < ts.length; i++) {
            WrapValue<Integer> index = WrapValue.of(i);
            ts[i] = AsynExecDecorator.run(() -> {
                for (int j = 0; j < 100; j++) {
                    this.execute(index.getValue());
                }
            });
        }

        for (int i = 0; i < ts.length; i++) {
            if (ts[i].isAlive()) {
                i--;
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("结果：" + count);
        System.out.println("用时：" + (endTime - startTime));
    }

}

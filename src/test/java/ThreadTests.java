import org.jiang.tools.lock.SpinLock;
import org.junit.Test;

/**
 * @author Bin
 * @date 2021/3/17 16:33
 */
public class ThreadTests {

    private int count = 0;
    private SpinLock lock = new SpinLock();


    public void execute() {
        try {
            Thread.sleep(10);
        } catch (Exception ignore) {
        }
        lock.lock();
        count++;
        lock.unlock();
    }

    @Test
    public void start() {
        long startTime = System.currentTimeMillis();
        Thread[] ts = new Thread[10];
        for (int i = 0; i < ts.length; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    this.execute();
                }
            });
            ts[i] = thread;
            thread.start();
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

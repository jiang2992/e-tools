import org.jiang.tools.decorator.RunTimeDecorator;
import org.junit.Test;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 其他测试类
 *
 * @author Bin
 * @since 1.0.0
 */
public class OtherTests {

    @Test
    public void test() throws InterruptedException {
        Thread.sleep(1000);
        int size = 60;

        RunTimeDecorator.run(() -> {
            for (int i = 0; i < 1000000; i++) {
                HashMap<Object, Object> map = new HashMap<>();
                for(int j = 0;j < size;j++){
                    map.put(j,"test");
                }
                map.get(size / 2);
            }
        }, time -> System.out.println("默认大小:" + time));

        RunTimeDecorator.run(() -> {
            for (int i = 0; i < 1000000; i++) {
                HashMap<Object, Object> map = new HashMap<>(size);
                for(int j = 0;j < size;j++){
                    map.put(j,"test");
                }
                map.get(size / 2);
            }
        }, time -> System.out.println("相同大小:" + time));

        int initialCapacity = Integer.highestOneBit((int) Math.ceil(size / 0.75) - 1) << 1;
        RunTimeDecorator.run(() -> {
            for (int i = 0; i < 1000000; i++) {
                HashMap<Object, Object> map = new HashMap<>(initialCapacity);
                for(int j = 0;j < size;j++){
                    map.put(j,"test");
                }
                map.get(size / 2);
            }
        }, time -> System.out.println("计算大小:" + time));
    }

    @Test
    public void test2() throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            int n = ThreadLocalRandom.current().nextInt(1200);
            System.out.println(n);
        }
    }

}

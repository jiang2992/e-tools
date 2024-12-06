import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.jiang.tools.decorator.RunTimeDecorator;
import org.jiang.tools.json.JsonUtils;
import org.junit.Test;

/**
 * 其他测试类
 *
 * @author Bin
 * @since 1.0.0
 */
public class OtherTests {

    /**
     * map的initialCapacity对性能的影响
     */
    @Test
    @SneakyThrows
    public void initialCapacity() {
        Thread.sleep(1000);
        int size = 60;

        RunTimeDecorator.run(() -> {
            for (int i = 0; i < 1000000; i++) {
                HashMap<Object, Object> map = new HashMap<>();
                for (int j = 0; j < size; j++) {
                    map.put(j, "test");
                }
                map.get(size / 2);
            }
        }, time -> System.out.println("默认大小:" + time));

        RunTimeDecorator.run(() -> {
            for (int i = 0; i < 1000000; i++) {
                HashMap<Object, Object> map = new HashMap<>(size);
                for (int j = 0; j < size; j++) {
                    map.put(j, "test");
                }
                map.get(size / 2);
            }
        }, time -> System.out.println("相同大小:" + time));

        int initialCapacity = Integer.highestOneBit((int) Math.ceil(size / 0.75) - 1) << 1;
        RunTimeDecorator.run(() -> {
            for (int i = 0; i < 1000000; i++) {
                HashMap<Object, Object> map = new HashMap<>(initialCapacity);
                for (int j = 0; j < size; j++) {
                    map.put(j, "test");
                }
                map.get(size / 2);
            }
        }, time -> System.out.println("计算大小:" + time));
    }

    /**
     * 随机数
     */
    @Test
    public void test2() {
        for (int i = 0; i < 1000; i++) {
            int n = ThreadLocalRandom.current().nextInt(1200);
            System.out.println(n);
        }
    }

    /**
     * 字符串拼接
     */
    @Test
    public void test3() {
        String str1 = "hello";
        String str2 = "he" + "llo";
        System.out.println(str1 == str2);
    }

}

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;
import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;
import org.jiang.tools.collection.list.SkipList;
import org.jiang.tools.decorator.RunTimeDecorator;
import org.jiang.tools.text.RandomUtils;
import org.junit.Test;

/**
 * 集合相关测试类
 *
 * @author Bin
 * @since 1.1.5
 */
public class CollectionTests {

    private final int ADD_COUNT = 10000;
    private final int CON_COUNT = 10000;
    private final int STR_LENGTH = 5;

    public static class Obj implements Comparable<Obj> {

        private final String name;
        private final Integer number;

        public Obj(String name, int number) {
            this.name = name;
            this.number = number;
        }

        @Override
        public boolean equals(Object o) {
            return super.equals(o);
        }

        @Override
        public int compareTo(Obj o) {
            return number.compareTo(o.number);
        }

        @Override
        public String toString() {
            return "Obj{" +
                    "name=" + name +
                    ",number=" + number +
                    '}';
        }
    }

    @Test
    public void skipListTest() {
        Obj a = new Obj("a", 10);
        Obj b = new Obj("b", 30);
        Obj c = new Obj("c", 20);
        Obj d = new Obj("d", 10);
        SkipList<Obj> list = new SkipList<>();
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);
        list.add(a);
        System.out.printf("first:%s,last:%s%n", list.getFirst(), list.getLast());
        System.out.println(list);

        list.remove(d);
        System.out.printf("first:%s,last:%s%n", list.getFirst(), list.getLast());
        System.out.println(list);

        list.removeLast();
        System.out.printf("first:%s,last:%s%n", list.getFirst(), list.getLast());
        System.out.println(list);

        list.removeFirst();
        System.out.printf("first:%s,last:%s%n", list.getFirst(), list.getLast());
        System.out.println(list);
    }

    @Test
    public void timeTest() {
        System.out.printf("插入对象个数: %s, 判断对象个数: %s, 删除对象个数: %s, 字符串长度: %s%n", ADD_COUNT, CON_COUNT, CON_COUNT,
                STR_LENGTH);

        runCollectionTest(new ArrayList<>());
        runCollectionTest(new LinkedList<>());
        runCollectionTest(new HashSet<>());
        runCollectionTest(new SkipList<>(30));
        runCollectionTest(new TreeSet<>());

//        runCollectionTest(new PriorityQueue<>());
    }

    public void runCollectionTest(Collection<String> collection) {
        long addTime = RunTimeDecorator.run(() -> {
            for (int i = 0; i < ADD_COUNT; i++) {
                String str = RandomUtils.generateLowerLetter(STR_LENGTH);
                collection.add(str);
            }
        });
        long conTime = RunTimeDecorator.run(() -> {
            for (int i = 0; i < CON_COUNT; i++) {
                String str = RandomUtils.generateLowerLetter(STR_LENGTH);
                collection.contains(str);
            }
        });
        long removeTime = RunTimeDecorator.run(() -> {
            for (int i = 0; i < CON_COUNT; i++) {
                String str = RandomUtils.generateLowerLetter(STR_LENGTH);
                collection.remove(str);
            }
        });
        long forTime = RunTimeDecorator.run(() -> {
            for (String s : collection)
                ;
        });
        System.out.println("-----------------------------------------");
        System.out.printf("%s: 插入耗时=%sms,判断耗时=%sms,删除耗时=%sms,遍历耗时=%s%n", collection.getClass().getSimpleName(), addTime,
                conTime, removeTime,
                forTime);
        System.out.printf("内存占用: %skb%n", ObjectSizeCalculator.getObjectSize(collection) / 1024);
        System.out.println("剩余对象大小: " + collection.size());

        System.gc();
    }

}

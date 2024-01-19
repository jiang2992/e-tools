import org.jiang.tools.math.EasyNumber;
import org.jiang.tools.math.RoundRule;
import org.junit.Test;

/**
 * 数字相关测试类
 *
 * @author Bin
 * @since 1.1.1
 */
public class NumberTests {

    @Test
    public void test() {
        EasyNumber easyNumber = EasyNumber.zero()
                .add(100)
                .multiply(EasyNumber.of(10).multiply(10))
                .add(800)
                .add(EasyNumber.of(400).add(400))
                .divide(9);
        System.out.println(easyNumber.toString());
        System.out.println(easyNumber.course());
        easyNumber.pow(2);
        System.out.println(easyNumber.course());
    }

    @Test
    public void test2() {
        EasyNumber easyNumber = EasyNumber.of(3);
        System.out.println(easyNumber.max(4.0));
        System.out.println(easyNumber.max(3.0));
        System.out.println(easyNumber.max(2.0));
        System.out.println(easyNumber.min(4.0));
        System.out.println(easyNumber.min(3.0));
        System.out.println(easyNumber.min(2));
    }

    @Test
    public void test3() {
        EasyNumber easyNumber = EasyNumber.of(1000);
        String doubleHexString = easyNumber.toDoubleHexString();
        System.out.println(doubleHexString);
        System.out.println(EasyNumber.ofDoubleHexString(doubleHexString));

        String hexString = easyNumber.toHexString();
        System.out.println(hexString);
        System.out.println(EasyNumber.ofHexString(hexString));
    }

}

import org.jiang.tools.math.EasyNumber;
import org.jiang.tools.math.EasyRatio;
import org.junit.Test;

/**
 * 数字相关测试类
 *
 * @author Bin
 * @since 1.1.1
 */
public class NumberTests {

    /**
     * 简单计算
     */
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

    /**
     * 值对比
     */
    @Test
    public void compare() {
        EasyNumber easyNumber = EasyNumber.of(3);
        System.out.println(easyNumber.max(4.0));
        System.out.println(easyNumber.max(3.0));
        System.out.println(easyNumber.max(2.0));
        System.out.println(easyNumber.min(4.0));
        System.out.println(easyNumber.min(3.0));
        System.out.println(easyNumber.min(2));
    }

    /**
     * 16进制转换
     */
    @Test
    public void hexString() {
        EasyNumber easyNumber = EasyNumber.of(1000);
        String doubleHexString = easyNumber.toDoubleHexString();
        System.out.println(doubleHexString);
        System.out.println(EasyNumber.ofDoubleHexString(doubleHexString));

        String hexString = easyNumber.toHexString();
        System.out.println(hexString);
        System.out.println(EasyNumber.ofHexString(hexString));
    }

    /**
     * 占比
     */
    @Test
    public void ratio() {
        EasyRatio easyRatio = EasyRatio.of(61).setScale(1);
        easyRatio.addTotal(24);
        System.out.println(easyRatio.take(3));
        System.out.println(easyRatio.take(35));
        System.out.println(easyRatio.take(24));
        System.out.println(easyRatio.take(23));
        System.out.println(easyRatio.remainedRatio());
    }

}

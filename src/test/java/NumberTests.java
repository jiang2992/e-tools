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
        String string = EasyNumber.zero().integerRule()
                .add(100)
                .multiply(EasyNumber.of(10).multiply(10))
                .add(800)
                .add(EasyNumber.of(400).add(400))
                .divide(9)
                .course();
        System.out.println(string);
    }

}
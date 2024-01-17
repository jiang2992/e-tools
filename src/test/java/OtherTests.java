import org.jiang.tools.decorator.AsynExecDecorator;
import org.jiang.tools.geo.EasyCoord;
import org.jiang.tools.geo.EasyGeo;
import org.jiang.tools.model.WrapValue;
import org.junit.Test;

/**
 * 其他测试类
 *
 * @author Bin
 * @since 1.0.0
 */
public class OtherTests {

    @Test
    public void test() throws InterruptedException {
        WrapValue<Integer> i = WrapValue.of(0);
        new AsynExecDecorator(() -> i.setValue(1)).run();
        Thread.sleep(100);
        System.out.println(i.getValue());
    }

}

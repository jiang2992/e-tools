import org.jiang.tools.decorator.AsynExecDecorator;
import org.jiang.tools.util.WrapValue;
import org.junit.Test;

/**
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

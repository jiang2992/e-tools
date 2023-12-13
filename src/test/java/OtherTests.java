import org.jiang.tools.decorator.AsynExecDecorator;
import org.jiang.tools.util.IdUtils;
import org.jiang.tools.util.WrapValue;
import org.junit.Test;

/**
 * @author Bin
 * @date 2020/12/17 15:03
 */
public class OtherTests {

    @Test
    public void test() throws InterruptedException {
        WrapValue i = WrapValue.of(0);
        new AsynExecDecorator(() -> {
            i.setValue(1);
        }).run();
        Thread.sleep(100);
        System.out.println(i.getValue());
    }

}

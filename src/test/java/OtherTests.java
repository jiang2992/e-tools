import org.jiang.tools.decorator.AsynExecDecorator;
import org.jiang.tools.geo.EasyGeo;
import org.jiang.tools.model.WrapValue;
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

    @Test
    public void geoTest() {
        EasyGeo data = EasyGeo.ofGcj02(116.41, 39.90);
        EasyGeo.Coord a = data.value();
        EasyGeo.Coord b = data.toWgs84().value();
        EasyGeo.Coord c = data.toGcj02().value();
        EasyGeo.Coord d = data.toBd09().value();
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);
    }

    @Test
    public void geoTest2() {
        System.out.println(EasyGeo.ofGcj02(116.41, 39.90).toWgs84().value());
    }

}

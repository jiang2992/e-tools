import lombok.SneakyThrows;
import org.jiang.tools.decorator.RunTimeDecorator;
import org.jiang.tools.geo.EasyCoord;
import org.jiang.tools.geo.EasyGeo;
import org.jiang.tools.geo.EasyGeoArea;
import org.junit.Test;

/**
 * 地理位置相关测试类
 *
 * @author Bin
 * @since 1.1.2
 */
public class GeoTests {

    /**
     * 位置计算和转换
     */
    @Test
    public void geoTest() {
        // 从Gcj02坐标系位置构造对象
        EasyGeo easyGeo = EasyGeo.ofGcj02(116.41, 39.90);

        // 计算与另一个坐标的距离（单位：米）
        long distance = easyGeo.calculateDistance(EasyGeo.ofGcj02(116.4101, 39.9001));

        // 转为Wgs84坐标系
        EasyCoord b = easyGeo.toWgs84().value();

        // 转为Bd09坐标系
        EasyCoord c = easyGeo.toBd09().value();

        System.out.println(b);
        System.out.println(c);
        System.out.println(distance);
    }

    /**
     * 区域计算
     */
    @Test
    public void areaTest() {
        EasyGeoArea easyGeoArea = EasyGeoArea.ofGcj02();
        easyGeoArea.add(116.395788, 39.907194);
        easyGeoArea.add(116.399296, 39.907336);
        easyGeoArea.add(116.399695, 39.900314);
        easyGeoArea.add(116.396073, 39.900205);
        RunTimeDecorator.run(() -> {
            System.out.println(easyGeoArea.contains(116.398112, 39.906002)); // true
            System.out.println(easyGeoArea.contains(116.39491, 39.923954)); // false
        }, System.out::println);
        RunTimeDecorator.run(() -> {
            System.out.println("面积：" + easyGeoArea.acreage());
        }, System.out::println);
        easyGeoArea.add(116.395118, 39.903366);
        RunTimeDecorator.run(() -> {
            System.out.println("面积：" + easyGeoArea.acreage());
        }, System.out::println);

    }

}

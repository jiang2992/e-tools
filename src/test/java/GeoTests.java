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

    @Test
    public void areaTest() {
        EasyGeoArea easyGeoArea = EasyGeoArea.ofGcj02();
        easyGeoArea.add(113.26207,23.145471);
        easyGeoArea.add(113.269384,23.14198);
        easyGeoArea.add(113.267876,23.137129);
//        easyGeoArea.add(113.261707,23.140748);
        boolean contains = easyGeoArea.contains(113.263801,23.141261);
//        boolean contains = easyGeoArea.contains(113.266062,23.145137);
        System.out.println(contains);
    }

}

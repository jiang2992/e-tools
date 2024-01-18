package org.jiang.tools.geo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 地理位置类
 *
 * @author Bin
 * @since 1.1.2
 */
@Getter
@AllArgsConstructor
public class EasyCoord {

    public static final byte COORD_SYS_WGS84 = 1;
    public static final byte COORD_SYS_GCJ02 = 2;
    public static final byte COORD_SYS_BD09 = 3;

    private final double lon;

    private final double lat;

    private final byte coordSys;

    /**
     * 使用GCJ02坐标系构建对象
     *
     * @param lon 经度
     * @param lat 纬度
     * @return EasyCoord
     */
    public static EasyCoord ofGcj02(double lon, double lat) {
        return new EasyCoord(lon, lat, COORD_SYS_GCJ02);
    }

    /**
     * 使用WGS84坐标系构建对象
     *
     * @param lon 经度
     * @param lat 纬度
     * @return EasyCoord
     */
    public static EasyCoord ofWgs84(double lon, double lat) {
        return new EasyCoord(lon, lat, COORD_SYS_WGS84);
    }

    /**
     * 使用BD09坐标系构建对象
     *
     * @param lon 经度
     * @param lat 纬度
     * @return EasyCoord
     */
    public static EasyCoord ofBd09(double lon, double lat) {
        return new EasyCoord(lon, lat, COORD_SYS_BD09);
    }

    public String getCoordSysText() {
        switch (coordSys) {
            case 1:
                return "WGS84";
            case 2:
                return "GCJ02";
            case 3:
                return "BD09";
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return String.format(String.format("%s:lon=%s,lat=%s", getCoordSysText(), lon, lat));
    }

}

package org.jiang.tools.geo;

/**
 * 经纬度转换器
 *
 * @author Bin
 * @since 1.1.0
 */
public class CoordinateConverter {

    /**
     * WGS-84椭球长半轴
     */
    private static final double SEMIMAJOR_AXIS = 6378245.0;

    /**
     * WGS-84椭球偏心率平方
     */
    private static final double ECCENTRICITY_SQUARED = 0.00669342162296594323;

    private static final double BD_PI = 52.35987755983;

    /**
     * 将WGS-84坐标转换为GCJ-02坐标
     *
     * @param lon 经度
     * @param lat 纬度
     * @return 包含转换后经度和纬度的数组
     */
    public static double[] wgs84ToGcj02(double lon, double lat) {
        return convert(lon, lat, true);
    }

    /**
     * 将GCJ-02坐标转换为WGS-84坐标
     *
     * @param lon 经度
     * @param lat 纬度
     * @return 包含转换后经度和纬度的数组
     */
    public static double[] gcj02ToWgs84(double lon, double lat) {
        return convert(lon, lat, false);
    }

    /**
     * 将GCJ-02坐标转换成BD-09坐标
     *
     * @param lon 经度
     * @param lat 纬度
     * @return 包含转换后经度和纬度的数组
     */
    public static double[] gcj02ToBd09(double lon, double lat) {
        double z = Math.sqrt(lon * lon + lat * lat) + 0.00002 * Math.sin(lat * BD_PI);
        double theta = Math.atan2(lat, lon) + 0.000003 * Math.cos(lon * BD_PI);
        double bd_lon = z * Math.cos(theta) + 0.0065;
        double bd_lat = z * Math.sin(theta) + 0.006;
        return new double[]{bd_lon, bd_lat};
    }

    /**
     * 将BD-09坐标转换成GCJ-02坐标
     *
     * @param lon 经度
     * @param lat 纬度
     * @return 包含转换后经度和纬度的数组
     */
    public static double[] bd09ToGcj02(double lon, double lat) {
        double x = lon - 0.0065;
        double y = lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * BD_PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * BD_PI);
        double gc_lon = z * Math.cos(theta);
        double gc_lat = z * Math.sin(theta);
        return new double[]{gc_lon, gc_lat};
    }

    /**
     * 将WGS-84坐标转换成BD-09坐标
     *
     * @param lon 经度
     * @param lat 纬度
     * @return 包含转换后经度和纬度的数组
     */
    public static double[] wgs84ToBd09(double lon, double lat) {
        double[] gcj02 = wgs84ToGcj02(lon, lat);
        return gcj02ToBd09(gcj02[0], gcj02[1]);
    }

    /**
     * 将BD-09坐标转换成WGS-84坐标
     *
     * @param lon 经度
     * @param lat 纬度
     * @return 包含转换后经度和纬度的数组
     */
    public static double[] bd09ToWgs84(double lon, double lat) {
        double[] gcj02 = bd09ToGcj02(lon, lat);
        return gcj02ToWgs84(gcj02[0], gcj02[1]);
    }

    /**
     * 将球形坐标转换成平面坐标
     *
     * @param lon 经度
     * @param lat 纬度
     * @return 包含转换后X轴和Y轴的数组
     */
    public static double[] toCartesian(double lon, double lat) {
        double x = lon * 20037508.34 / 180;
        double y = Math.log(Math.tan((90 + lat) * Math.PI / 360)) / (Math.PI / 180);
        y = y * 20037508.34 / 180;
        return new double[]{x, y};
    }

    /**
     * 将坐标进行转换
     *
     * @param lon     经度
     * @param lat     纬度
     * @param toGcj02 true表示WGS-84转GCJ-02，false表示GCJ-02转WGS-84
     * @return 包含转换后经度和纬度的数组
     */
    private static double[] convert(double lon, double lat, boolean toGcj02) {
        double[] result = new double[2];

        if (needToConvert(lon, lat)) {
            double dLon = calculateLonOffset(lon - 105.0, lat - 35.0);
            double dLat = calculateLatOffset(lon - 105.0, lat - 35.0);
            double radLat = lat / 180.0 * Math.PI;
            double magic = Math.sin(radLat);
            magic = 1 - ECCENTRICITY_SQUARED * magic * magic;
            double sqrtMagic = Math.sqrt(magic);
            dLon = (dLon * 180.0) / (SEMIMAJOR_AXIS / sqrtMagic * Math.cos(radLat) * Math.PI);
            dLat = (dLat * 180.0) / ((SEMIMAJOR_AXIS * (1 - ECCENTRICITY_SQUARED)) / (magic * sqrtMagic) * Math.PI);

            result[0] = toGcj02 ? lon + dLon : lon - dLon;
            result[1] = toGcj02 ? lat + dLat : lat - dLat;
        } else {
            result[0] = lon;
            result[1] = lat;
        }

        return result;
    }

    /**
     * 判断坐标是否需要转换
     *
     * @param lon 经度
     * @param lat 纬度
     * @return 是否需要转换
     */
    private static boolean needToConvert(double lon, double lat) {
        return isInChina(lon, lat);
    }

    /**
     * 判断坐标是否在中国范围内
     *
     * @param lon 经度
     * @param lat 纬度
     * @return 是否在中国范围内
     */
    private static boolean isInChina(double lon, double lat) {
        return lon >= 72.004 && lon <= 137.8347 && lat >= 0.8293 && lat <= 55.8271;
    }

    /**
     * 计算经度偏移量
     *
     * @param lon 经度
     * @param lat 纬度
     * @return 偏移量
     */
    private static double calculateLonOffset(double lon, double lat) {
        double ret = 300.0 + lon + 2.0 * lat + 0.1 * lon * lon + 0.1 * lon * lat + 0.1 * Math.sqrt(Math.abs(lon));
        ret += (20.0 * Math.sin(6.0 * lon * Math.PI) + 20.0 * Math.sin(2.0 * lon * Math.PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lon * Math.PI) + 40.0 * Math.sin(lon / 3.0 * Math.PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(lon / 12.0 * Math.PI) + 300.0 * Math.sin(lon / 30.0 * Math.PI)) * 2.0 / 3.0;
        return ret;
    }

    /**
     * 计算纬度偏移量
     *
     * @param lon 经度
     * @param lat 纬度
     * @return 偏移量
     */
    private static double calculateLatOffset(double lon, double lat) {
        double ret = -100.0 + 2.0 * lon + 3.0 * lat + 0.2 * lat * lat + 0.1 * lon * lat + 0.2 * Math.sqrt(Math.abs(lon));
        ret += (20.0 * Math.sin(6.0 * lon * Math.PI) + 20.0 * Math.sin(2.0 * lon * Math.PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lat * Math.PI) + 40.0 * Math.sin(lat / 3.0 * Math.PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(lat / 12.0 * Math.PI) + 320 * Math.sin(lat * Math.PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

}

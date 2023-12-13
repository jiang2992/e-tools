package org.jiang.tools.util;

/**
 * 地理位置工具类
 *
 * @author Bin
 * @date 2020-10-27 09:42
 */
public class GeoUtils {

    private static final double PI = 3.1415926535897932384626433832795;
    private static final double A = 6378245.0;
    private static final double EE = 0.00669342162296594323;

    /**
     * 计算两地距离（定位：米）
     *
     * @param lon1 A位置经度
     * @param lat1 A位置纬度
     * @param lon2 B位置经度
     * @param lat2 B位置纬度
     * @return 米
     */
    public static Long calculateDistance(double lon1, double lat1, double lon2, double lat2) {
        double a, b, r;
        // 地球半径
        r = 6378137;
        lat1 = lat1 * Math.PI / 180.0;
        lat2 = lat2 * Math.PI / 180.0;
        a = lat1 - lat2;
        b = (lon1 - lon2) * Math.PI / 180.0;
        double d;
        double sa2, sb2;
        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2
                * r
                * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)
                * Math.cos(lat2) * sb2 * sb2));
        return Double.valueOf(d).longValue();
    }

    /**
     * 地球坐标系 WGS-84 to 火星坐标系 GCJ-02
     *
     * @param latitude  纬度
     * @param longitude 经度
     * @return 经纬度
     */
    public static Point calWgs84ToGcj02(double longitude, double latitude) {
        Point dev = calDev(latitude, longitude);
        double retLat = latitude + dev.getLatitude();
        double retLon = longitude + dev.getLongitude();
        return new Point(retLat, retLon);
    }

    /**
     * 火星坐标系 GCJ-02 to 地球坐标系 WGS-84
     *
     * @param latitude  纬度
     * @param longitude 经度
     * @return 经纬度
     */
    public static Point calGcj02ToWgs84(double longitude, double latitude) {
        Point dev = calDev(latitude, longitude);
        double retLat = latitude - dev.getLatitude();
        double retLon = longitude - dev.getLongitude();
        dev = calDev(retLat, retLon);
        retLat = latitude - dev.getLatitude();
        retLon = longitude - dev.getLongitude();
        return new Point(retLat, retLon);
    }

    private static Point calDev(double latitude, double longitude) {
        double dLat = calLat(longitude - 105.0, latitude - 35.0);
        double dLon = calLon(longitude - 105.0, latitude - 35.0);
        double radLat = latitude / 180.0 * PI;
        double magic = Math.sin(radLat);
        magic = 1 - EE * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((A * (1 - EE)) / (magic * sqrtMagic) * PI);
        dLon = (dLon * 180.0) / (A / sqrtMagic * Math.cos(radLat) * PI);
        return new Point(dLat, dLon);
    }

    private static double calLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * PI) + 40.0 * Math.sin(y / 3.0 * PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * PI) + 320 * Math.sin(y * PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    private static double calLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * PI) + 40.0 * Math.sin(x / 3.0 * PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * PI) + 300.0 * Math.sin(x / 30.0 * PI)) * 2.0 / 3.0;
        return ret;
    }

    public static class Point {
        private double longitude;
        private double latitude;

        Point(double latitude, double longitude) {
            this.longitude = longitude;
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        @Override
        public String toString() {
            return longitude + "," + latitude;
        }
    }

}

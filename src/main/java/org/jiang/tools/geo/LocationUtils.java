package org.jiang.tools.geo;

/**
 * @author Bin
 * @date 2023/12/18 17:39
 */
public class LocationUtils {

    private static final double SEMIMAJOR_AXIS = 6378245.0; // WGS-84椭球长半轴

    /**
     * 计算两个经纬度之间的距离
     *
     * @param lon1 第一个点的经度
     * @param lat1 第一个点的纬度
     * @param lon2 第二个点的经度
     * @param lat2 第二个点的纬度
     * @return 两个点之间的距离（单位：米）
     */
    public static long calculateDistance(double lon1, double lat1, double lon2, double lat2) {
        double radLat1 = lat1 * Math.PI / 180.0;
        double radLat2 = lat2 * Math.PI / 180.0;
        double a = radLat1 - radLat2;
        double b = lon1 * Math.PI / 180.0 - lon2 * Math.PI / 180.0;
        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
                Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        distance = distance * SEMIMAJOR_AXIS;
        distance = Math.round(distance * 10000) / 10.0;
        return (long) distance;
    }

}

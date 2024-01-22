package org.jiang.tools.geo;


import java.awt.geom.Point2D;
import java.util.List;

/**
 * 地理位置工具类
 *
 * @author Bin
 * @since 1.1.0
 */
public class LocationUtils {

    private static final double SEMIMAJOR_AXIS = 6378245.0; // WGS-84椭球长半轴

    private static final double METERS_DEGREE = 2.0 * Math.PI * SEMIMAJOR_AXIS / 360.0;

    private static final double RADIANS_DEGREE = Math.PI / 180.0;


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

    /**
     * 计算区域面积
     *
     * @param pointList 顶点列表
     * @return 面积（单位：平方米）
     */
    public static double calculateAcreage(List<Point2D.Double> pointList) {
        double totalAngle = 0;
        int size = pointList.size();
        for (int i = 0; i < pointList.size(); i++) {
            int i2 = (i + 1) % size;
            int i3 = (i + 2) % size;

            double bearing21 = getBearing(pointList.get(i2), pointList.get(i));
            double bearing23 = getBearing(pointList.get(i2), pointList.get(i3));
            double angle = bearing21 - bearing23;
            if (angle < 0) {
                angle += 360;
            }

            totalAngle += angle;
        }

        double planarTotalAngle = (size - 2) * 180.0;

        double sphericalExcess = totalAngle - planarTotalAngle;
        if (sphericalExcess > 420.0) {
            totalAngle = size * 360.0 - totalAngle;
            sphericalExcess = totalAngle - planarTotalAngle;
        } else if (sphericalExcess > 300.0 && sphericalExcess < 420.0) {
            sphericalExcess = Math.abs(360.0 - sphericalExcess);
        }

        return sphericalExcess * RADIANS_DEGREE * SEMIMAJOR_AXIS * SEMIMAJOR_AXIS;
    }

    private static double getBearing(Point2D.Double from, Point2D.Double to) {
        double lat1 = from.getY() * RADIANS_DEGREE;
        double lon1 = from.getX() * RADIANS_DEGREE;
        double lat2 = to.getY() * RADIANS_DEGREE;
        double lon2 = to.getX() * RADIANS_DEGREE;
        double angle = -Math.atan2(
                Math.sin(lon1 - lon2) * Math.cos(lat2),
                Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

        if (angle < 0) {
            angle += Math.PI * 2.0;
        }
        angle = angle * 180.0 / Math.PI;
        return angle;
    }

}

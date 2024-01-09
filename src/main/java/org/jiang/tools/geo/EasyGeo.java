package org.jiang.tools.geo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jiang.tools.exception.BadArgumentException;

/**
 * EasyGeo：提供地理位置的坐标系转换及计算功能
 *
 * @author Bin
 * @since 1.1.0
 */
public class EasyGeo {

    public static final byte COORD_SYS_WGS84 = 1;
    public static final byte COORD_SYS_GCJ02 = 2;
    public static final byte COORD_SYS_BD09 = 3;

    private Coord coord;

    public EasyGeo(Coord coord) {
        this.coord = coord;
    }

    public static EasyGeo of(Coord coord) {
        return new EasyGeo(coord);
    }

    /**
     * 使用GCJ02坐标系构建对象
     *
     * @param lon 经度
     * @param lat 纬度
     * @return EasyGeo
     */
    public static EasyGeo ofGcj02(double lon, double lat) {
        Coord coord = new Coord(lon, lat, COORD_SYS_GCJ02);
        return of(coord);
    }

    /**
     * 使用WGS84坐标系构建对象
     *
     * @param lon 经度
     * @param lat 纬度
     * @return EasyGeo
     */
    public static EasyGeo ofWgs84(double lon, double lat) {
        Coord coord = new Coord(lon, lat, COORD_SYS_WGS84);
        return of(coord);
    }

    /**
     * 使用BD09坐标系构建对象
     *
     * @param lon 经度
     * @param lat 纬度
     * @return EasyGeo
     */
    public static EasyGeo ofBd09(double lon, double lat) {
        Coord coord = new Coord(lon, lat, COORD_SYS_BD09);
        return of(coord);
    }

    public Coord value() {
        return this.coord;
    }

    /**
     * 转为WGS84坐标系
     *
     * @return EasyGeo
     */
    public EasyGeo toWgs84() {
        double[] result = null;
        switch (this.coord.coord_sys) {
            case COORD_SYS_GCJ02:
                result = CoordinateConverter.gcj02ToWgs84(coord.lon, coord.lat);
                break;
            case COORD_SYS_BD09:
                result = CoordinateConverter.bd09ToWgs84(coord.lon, coord.lat);
                break;
        }
        if (result != null) {
            this.coord = new Coord(result[0], result[1], COORD_SYS_WGS84);
        }
        return this;
    }

    /**
     * 转为GCJ02坐标系
     *
     * @return EasyGeo
     */
    public EasyGeo toGcj02() {
        double[] result = null;
        switch (this.coord.coord_sys) {
            case COORD_SYS_WGS84:
                result = CoordinateConverter.wgs84ToGcj02(coord.lon, coord.lat);
                break;
            case COORD_SYS_BD09:
                result = CoordinateConverter.bd09ToGcj02(coord.lon, coord.lat);
                break;
        }
        if (result != null) {
            this.coord = new Coord(result[0], result[1], COORD_SYS_GCJ02);
        }
        return this;
    }

    /**
     * 转为BD09坐标系
     *
     * @return EasyGeo
     */
    public EasyGeo toBd09() {
        double[] result = null;
        switch (this.coord.coord_sys) {
            case COORD_SYS_WGS84:
                result = CoordinateConverter.wgs84ToBd09(coord.lon, coord.lat);
                break;
            case COORD_SYS_GCJ02:
                result = CoordinateConverter.gcj02ToBd09(coord.lon, coord.lat);
                break;
        }
        if (result != null) {
            this.coord = new Coord(result[0], result[1], COORD_SYS_BD09);
        }
        return this;
    }

    /**
     * 计算与目标位置的距离
     *
     * @param easyGeo 目标位置
     * @return 距离（单位：米）
     */
    public long calculateDistance(EasyGeo easyGeo) {
        if (easyGeo.coord.coord_sys != this.coord.coord_sys) {
            throw new BadArgumentException(String.format("所用坐标系不同，无法进行计算：%s,%s", this.coord.getCoordSysText(), easyGeo.coord.getCoordSysText()));
        }
        return this.calculateDistance(easyGeo.coord.lon, easyGeo.coord.lat);
    }

    /**
     * 计算与目标位置的距离
     * 注意：调用改方法时，请保证所用坐标系一致
     *
     * @param lon 经度
     * @param lat 纬度
     * @return 距离（单位：米）
     */
    public long calculateDistance(double lon, double lat) {
        return LocationUtils.calculateDistance(this.coord.lon, this.coord.lat, lon, lat);
    }

    @Data
    @AllArgsConstructor
    public static class Coord {

        private double lon;

        private double lat;

        private byte coord_sys;

        public String getCoordSysText() {
            switch (coord_sys) {
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

}

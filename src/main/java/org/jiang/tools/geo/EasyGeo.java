package org.jiang.tools.geo;

import org.jiang.tools.exception.BadArgumentException;

/**
 * EasyGeo：提供地理位置的坐标系转换及计算功能
 *
 * @author Bin
 * @since 1.1.0
 */
public class EasyGeo {

    private EasyCoord coord;

    public EasyGeo(EasyCoord coord) {
        this.coord = coord;
    }

    public static EasyGeo of(EasyCoord coord) {
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
        EasyCoord coord = EasyCoord.ofGcj02(lon, lat);
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
        EasyCoord coord = EasyCoord.ofWgs84(lon, lat);
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
        EasyCoord coord = EasyCoord.ofBd09(lon, lat);
        return of(coord);
    }

    public EasyCoord value() {
        return this.coord;
    }

    /**
     * 转为WGS84坐标系
     *
     * @return EasyGeo
     */
    public EasyGeo toWgs84() {
        double[] result = null;
        switch (this.coord.getCoordSys()) {
            case EasyCoord.COORD_SYS_GCJ02:
                result = CoordinateConverter.gcj02ToWgs84(coord.getLon(), coord.getLat());
                break;
            case EasyCoord.COORD_SYS_BD09:
                result = CoordinateConverter.bd09ToWgs84(coord.getLon(), coord.getLat());
                break;
        }
        if (result != null) {
            this.coord = EasyCoord.ofWgs84(result[0], result[1]);
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
        switch (this.coord.getCoordSys()) {
            case EasyCoord.COORD_SYS_WGS84:
                result = CoordinateConverter.wgs84ToGcj02(coord.getLon(), coord.getLat());
                break;
            case EasyCoord.COORD_SYS_BD09:
                result = CoordinateConverter.bd09ToGcj02(coord.getLon(), coord.getLat());
                break;
        }
        if (result != null) {
            this.coord = EasyCoord.ofGcj02(result[0], result[1]);
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
        switch (this.coord.getCoordSys()) {
            case EasyCoord.COORD_SYS_WGS84:
                result = CoordinateConverter.wgs84ToBd09(coord.getLon(), coord.getLat());
                break;
            case EasyCoord.COORD_SYS_GCJ02:
                result = CoordinateConverter.gcj02ToBd09(coord.getLon(), coord.getLat());
                break;
        }
        if (result != null) {
            this.coord = EasyCoord.ofBd09(result[0], result[1]);
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
        return this.calculateDistance(easyGeo.coord);
    }

    /**
     * 计算与目标位置的距离
     *
     * @param easyCoord 目标位置
     * @return 距离（单位：米）
     */
    public long calculateDistance(EasyCoord easyCoord) {
        if (easyCoord.getCoordSys() != this.coord.getCoordSys()) {
            throw new BadArgumentException(String.format("所用坐标系不同，无法进行计算：%s,%s", this.coord.getCoordSysText(), easyCoord.getCoordSysText()));
        }
        return this.calculateDistance(easyCoord.getLon(), easyCoord.getLat());
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
        return LocationUtils.calculateDistance(this.coord.getLon(), this.coord.getLat(), lon, lat);
    }

}

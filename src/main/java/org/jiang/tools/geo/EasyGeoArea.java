package org.jiang.tools.geo;

import org.jiang.tools.exception.BadArgumentException;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * EasyGeoArea：提供地理位置区域的计算
 *
 * @author Bin
 * @since 1.1.2
 */
public class EasyGeoArea {

    private Path2D.Double path;

    private byte defaultCoordSys = -1;

    private boolean fixed = false;

    private long acreage = -1;

    /**
     * 坐标点链表
     */
    private final LinkedList<EasyCoord> coordList = new LinkedList<>();

    public EasyGeoArea() {
    }

    public EasyGeoArea(List<EasyCoord> coordList) {
        if (coordList == null || coordList.isEmpty()) {
            return;
        }
        this.defaultCoordSys = coordList.get(0).getCoordSys();
        this.coordList.addAll(coordList);
        this.coordList.forEach(this::lineTo);
    }

    /**
     * 使用一组坐标点构造对象
     *
     * @param coordList 坐标点集合
     * @return EasyGeoArea
     */
    public static EasyGeoArea of(List<EasyCoord> coordList) {
        return new EasyGeoArea(coordList);
    }

    /**
     * 构造一个空的Gcj02坐标系区域对象
     *
     * @return EasyGeoArea
     */
    public static EasyGeoArea ofGcj02() {
        return coordSys(EasyCoord.COORD_SYS_GCJ02);
    }

    /**
     * 构造一个空的Wgs84坐标系区域对象
     *
     * @return EasyGeoArea
     */
    public static EasyGeoArea ofWgs84() {
        return coordSys(EasyCoord.COORD_SYS_WGS84);
    }

    /**
     * 构造一个空的Bd09坐标系区域对象
     *
     * @return EasyGeoArea
     */
    public static EasyGeoArea ofBd09() {
        return coordSys(EasyCoord.COORD_SYS_BD09);
    }

    /**
     * 构造一个空的指定坐标系区域对象
     *
     * @param coordSys 坐标系
     * @return EasyGeoArea
     * @see EasyCoord
     */
    public static EasyGeoArea coordSys(byte coordSys) {
        EasyGeoArea easyGeoArea = new EasyGeoArea();
        return easyGeoArea.setCoordSys(coordSys);
    }

    /**
     * 设置坐标系
     *
     * @param coordSys 坐标系
     * @return EasyGeoArea
     * @see EasyCoord
     */
    public EasyGeoArea setCoordSys(byte coordSys) {
        this.defaultCoordSys = coordSys;
        return this;
    }

    /**
     * 添加坐标点集合
     *
     * @param coordList 坐标点集合
     * @return EasyGeoArea
     */
    public EasyGeoArea add(List<EasyCoord> coordList) {
        if (coordList == null || coordList.isEmpty()) {
            return this;
        }
        coordList.forEach(this::add);
        return this;
    }

    /**
     * 添加坐标点
     *
     * @param easyCoord 坐标点
     * @return EasyGeoArea
     */
    public EasyGeoArea add(EasyCoord easyCoord) {
        if (defaultCoordSys == -1) {
            this.defaultCoordSys = easyCoord.getCoordSys();
        }
        if (this.fixed) {
            this.reset();
        }
        this.coordList.addLast(easyCoord);
        this.acreage = -1;
        this.lineTo(easyCoord);
        return this;
    }

    /**
     * 使用默认坐标系添加坐标点
     * 需先设置默认坐标系
     *
     * @param lon 经度
     * @param lat 纬度
     * @return EasyGeoArea
     */
    public EasyGeoArea add(double lon, double lat) {
        if (defaultCoordSys == -1) {
            throw new BadArgumentException("无默认坐标系");
        }
        return this.add(new EasyCoord(lon, lat, this.defaultCoordSys));
    }

    private void lineTo(EasyCoord easyCoord) {
        double[] cartesian = CoordinateConverter.toCartesian(easyCoord.getLon(), easyCoord.getLat());
        if (this.path == null) {
            this.path = new Path2D.Double();
            this.path.moveTo(cartesian[0], cartesian[1]);
            return;
        }
        this.path.lineTo(cartesian[0], cartesian[1]);
    }

    /**
     * 判断目标坐标点是否在区域中
     * 调用该方法时会自动将所有坐标点构建成一个多边形区域（至少需要3个坐标点）
     *
     * @param easyCoord 目标坐标点
     * @return 是否在区域中
     */
    public boolean contains(EasyCoord easyCoord) {
        return this.contains(easyCoord.getLon(), easyCoord.getLat());
    }

    /**
     * 判断目标坐标点是否在区域中
     * 调用该方法时会自动将所有坐标点构建成一个多边形区域（至少需要3个坐标点）
     *
     * @param lon 经度
     * @param lat 纬度
     * @return 是否在区域中
     */
    public boolean contains(double lon, double lat) {
        this.fixed();
        double[] cartesian = CoordinateConverter.toCartesian(lon, lat);
        return this.path.contains(cartesian[0], cartesian[1]);
    }

    /**
     * 计算区域面积
     *
     * @return 区域面积（单位：平方米）
     */
    public long acreage() {
        if (acreage != -1) {
            return acreage;
        }
        if (this.coordList.size() < 3) {
            throw new BadArgumentException("至少需要添加3个坐标点");
        }
        List<Point2D.Double> collect = this.coordList.stream().map(item -> new Point2D.Double(item.getLon(), item.getLat())).collect(Collectors.toList());
        this.acreage = (long) LocationUtils.calculateAcreage(collect);
        return this.acreage;
    }

    private void fixed() {
        if (this.coordList.size() < 3) {
            throw new BadArgumentException("至少需要添加3个坐标点");
        }
        if (this.fixed) {
            return;
        }
        this.lineTo(coordList.getFirst());
        this.path.closePath();
        this.fixed = true;
    }

    private void reset() {
        if (this.path == null) {
            return;
        }
        this.path = null;
        this.fixed = false;
        this.acreage = -1;
        this.coordList.forEach(this::lineTo);
    }

}

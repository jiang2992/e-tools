package org.jiang.tools.geo;

import org.jiang.tools.exception.BadArgumentException;

import java.awt.geom.Path2D;
import java.util.LinkedList;
import java.util.List;

/**
 * EasyGeoArea：提供地理位置区域的计算
 *
 * @author Bin
 * @since 1.1.2
 */
public class EasyGeoArea {

    private Path2D.Double path;

    private byte defaultCoordSys = 0;

    private boolean fixed = false;

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

    public static EasyGeoArea of(List<EasyCoord> coordList) {
        return new EasyGeoArea(coordList);
    }

    public static EasyGeoArea ofGcj02() {
        return coordSys(EasyCoord.COORD_SYS_GCJ02);
    }

    public static EasyGeoArea ofWgs84() {
        return coordSys(EasyCoord.COORD_SYS_WGS84);
    }

    public static EasyGeoArea ofBd09() {
        return coordSys(EasyCoord.COORD_SYS_BD09);
    }

    public static EasyGeoArea coordSys(byte coordSys) {
        EasyGeoArea easyGeoArea = new EasyGeoArea();
        return easyGeoArea.setCoordSys(coordSys);
    }

    public EasyGeoArea setCoordSys(byte coordSys) {
        this.defaultCoordSys = coordSys;
        return this;
    }

    public EasyGeoArea add(List<EasyCoord> coordList) {
        if (coordList == null || coordList.isEmpty()) {
            return this;
        }
        coordList.forEach(this::add);
        return this;
    }

    public EasyGeoArea add(EasyCoord easyCoord) {
        if (defaultCoordSys == 0) {
            this.defaultCoordSys = easyCoord.getCoordSys();
        }
        this.coordList.addLast(easyCoord);
        this.lineTo(easyCoord);
        return this;
    }

    public EasyGeoArea add(double lon, double lat) {
        if (defaultCoordSys == 0) {
            throw new BadArgumentException("无默认坐标系");
        }
        return this.add(new EasyCoord(lon, lat, this.defaultCoordSys));
    }

    private void lineTo(EasyCoord easyCoord) {
        if (this.path == null) {
            this.path = new Path2D.Double();
            this.path.moveTo(easyCoord.getLon(), easyCoord.getLat());
            return;
        }
        this.path.lineTo(easyCoord.getLon(), easyCoord.getLat());
    }

    public boolean contains(EasyCoord easyCoord) {
        return this.contains(easyCoord.getLon(), easyCoord.getLat());
    }

    public boolean contains(double lon, double lat) {
        this.fixed();
        return this.path.contains(lon, lat);
    }

    public EasyGeoArea fixed() {
        if (this.coordList.size() < 3) {
            throw new BadArgumentException("至少需要添加3个坐标点");
        }
        if (this.fixed) {
            return this;
        }
        path.lineTo(coordList.getFirst().getLon(), coordList.getFirst().getLat());
        path.closePath();
        this.fixed = true;
        return this;
    }

}

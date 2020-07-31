package com.silent.utils;

import org.apache.poi.sl.usermodel.Line;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

//import com.github.davidmoten.rtree.geometry.Line;
//import com.github.davidmoten.rtree.geometry.internal.LineDouble;

/**
 * @author
 * @Title: SpatialUtil
 * @ProjectName CODE-X
 * @Description:
 * @date 2020/7/21 17:37
 */
public class SpatialUtil {

    private static double pai = 3.14159265358979324;
    private static double a = 6378245.0;
    private static double ee = 0.00669342162296594323;
    private final static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

    /**
     * 84to火星坐标系(GCJ - 02)World Geodetic System==>Mars Geodetic System
     * 火星坐标系(GCJ-02)与百度坐标系(BD-09)的转换算法将GCJ-02坐标转换成BD-0 坐标
     * (1. WGS84坐标系：即地球坐标系，国际上通用的坐标系。设备一般包含GPS芯片或者北斗芯片获取的经纬度为WGS84地理坐标系,
     * 谷歌地图采用的是WGS84地理坐标系（中国范围除外）;
     * 2.BD09坐标系：即百度坐标系，GCJ02坐标系经加密后的坐标系;)
     */
    public static double[] wgs2bd(double lat, double lon) {
        double[] wgs2gcj = wgs2gcj(lat, lon);
        return gcj2bd(wgs2gcj[0], wgs2gcj[1]);
    }

    public static double[] bd2wgs(double lat, double lon) {
        double[] bd2gcj = bd2gcj(lat, lon);
        return gcj2wgs(bd2gcj[0], bd2gcj[1]);
    }

    /**
     * 火星坐标系(GCJ-02)与百度坐标系(BD-09)的转换算法，将GCJ-02坐标转换成BD-09坐标
     */
    public static double[] gcj2bd(double lat, double lon) {
        double z = Math.sqrt(lon * lon + lat * lat) + 0.00002 * Math.sin(lat * x_pi);
        double theta = Math.atan2(lat, lon) + 0.000003 * Math.cos(lon * x_pi);
        double bd_lon = z * Math.cos(theta) + 0.0065;
        double bd_lat = z * Math.sin(theta) + 0.006;
        return new double[]{bd_lat, bd_lon};
    }

    /**
     * 百度坐标系(BD - 09)与火星坐标系(GCJ - 02)的转换算法，将BD-09坐标转换成GCJ-02坐标
     */
    public static double[] bd2gcj(double lat, double lon) {
        double x = lon - 0.0065, y = lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        double gg_lon = z * Math.cos(theta);
        double gg_lat = z * Math.sin(theta);
        return new double[]{gg_lat, gg_lon};
    }

    /**
     * wgs84  to  火星坐标系  (GCJ-02)  World  Geodetic  System  ==>  Mars  Geodetic  System
     */
    public static double[] wgs2gcj(double lat, double lon) {
        double dLat = transformLat(lon - 105.0, lat - 35.0);
        double dLon = transformLon(lon - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * pai;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pai);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pai);
        double mgLat = lat + dLat;
        double mgLon = lon + dLon;
        return new double[]{mgLat, mgLon};
    }

    public static double[] gcj2wgs(double lat, double lng) {
        double dlat = transformLat(lng - 105.0, lat - 35.0);
        double dlng = transformLon(lng - 105.0, lat - 35.0);
        double radlat = lat / 180.0 * pai;
        double magic = Math.sin(radlat);
        magic = 1 - ee * magic * magic;
        double sqrtmagic = Math.sqrt(magic);
        dlat = (dlat * 180.0) / ((a * (1 - ee)) / (magic * sqrtmagic) * pai);
        dlng = (dlng * 180.0) / (a / sqrtmagic * Math.cos(radlat) * pai);
        double mglat = lat + dlat;
        double mglng = lng + dlng;
        return new double[]{lat * 2 - mglat, lng * 2 - mglng};
    }

    private static double transformLat(double lat, double lon) {
        double ret = -100.0 + 2.0 * lat + 3.0 * lon + 0.2 * lon * lon + 0.1 * lat * lon + 0.2 * Math.sqrt(Math.abs(lat));
        ret += (20.0 * Math.sin(6.0 * lat * pai) + 20.0 * Math.sin(2.0 * lat * pai)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lon * pai) + 40.0 * Math.sin(lon / 3.0 * pai)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(lon / 12.0 * pai) + 320 * Math.sin(lon * pai / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    private static double transformLon(double lat, double lon) {
        double ret = 300.0 + lat + 2.0 * lon + 0.1 * lat * lat + 0.1 * lat * lon + 0.1 * Math.sqrt(Math.abs(lat));
        ret += (20.0 * Math.sin(6.0 * lat * pai) + 20.0 * Math.sin(2.0 * lat * pai)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lat * pai) + 40.0 * Math.sin(lat / 3.0 * pai)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(lat / 12.0 * pai) + 300.0 * Math.sin(lat / 30.0 * pai)) * 2.0 / 3.0;
        return ret;
    }

    public static Geometry getGeometry(String wkt) {
        // GeometryFactory工厂，参数一：数据精度 参数二空间参考系SRID
//        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING), 4326);
        GeometryFactory geometryFactory = new GeometryFactory();

        // 熟知文本WKT阅读器，可以将WKT文本转换为Geometry对象
        WKTReader wktReader = new WKTReader(geometryFactory);

        // Geometry对象，包含Point、LineString、Polygon等子类
        try {
            return wktReader.read(wkt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("wktReader.read 出错：" + wkt);
    }

    public static LineString createLine(Coordinate[] coords) {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING), 4326);
        return geometryFactory.createLineString(coords);
    }

//    public static Line getLineRectangle(LineString line) {
//        Envelope envelopeInternal = line.getEnvelopeInternal();
//        double minX = envelopeInternal.getMinX();
//        double minY = envelopeInternal.getMinY();
//        double maxX = envelopeInternal.getMaxX();
//        double maxY = envelopeInternal.getMaxY();
//        //得到RTree类型的最小矩形
//        return LineDouble.create(minX, minY, maxX, maxY);
//    }

}

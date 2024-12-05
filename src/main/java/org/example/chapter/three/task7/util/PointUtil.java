package org.example.chapter.three.task7.util;

import org.example.chapter.three.task7.entity.Point;
import org.example.chapter.three.task7.entity.RationalFraction;

public class PointUtil {
    private final static double precision = 1e-10;
    public static boolean isPointOnLine(Point p1, Point p2, Point p3) {
        if (p1.equals(p2) || p1.equals(p3) || p2.equals(p3)) return true;
        Point v12 = new Point(p1, p2);
        Point v13 = new Point(p1, p3);
        RationalFraction dotProduct = v13.x().multiply(v12.x()).add(v13.y().multiply(v12.y())).add(v13.z().multiply(v12.z()));
        return Math.abs(dotProduct.getNumerator() / (dotProduct.getDenominator() * Distance.distanceTo(v12) * Distance.distanceTo(v13)) - 1) <= precision;
    }
}

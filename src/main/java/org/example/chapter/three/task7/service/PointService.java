package org.example.chapter.three.task7.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.chapter.three.task7.entity.Point;
import org.example.chapter.three.task7.entity.RationalFraction;

public class PointService {
    private final static Logger LOGGER = LogManager.getLogger(PointService.class);
    private final static double precision = 1e-10;

    /***
     * Determines whether three points ore on the line
     *
     * @param p1 first point
     * @param p2 second point
     * @param p3 third point
     * @return true if points are on the line
     */
    public static boolean isPointOnLine(Point p1, Point p2, Point p3) {
        LOGGER.debug("p1 = {}, p2 = {}, p3 = {}", p1, p2, p3);
        if (p1.equals(p2) || p1.equals(p3) || p2.equals(p3)) return true;
        Point v12 = new Point(p1, p2);
        Point v13 = new Point(p1, p3);
        RationalFraction dotProduct = v13.x().multiply(v12.x())
                                         .add(v13.y().multiply(v12.y()))
                                         .add(v13.z().multiply(v12.z()));
        return Math.abs(
                dotProduct.getNumerator() / (
                        dotProduct.getDenominator()
                        * v12.distanceFromOrigin()
                        * v13.distanceFromOrigin()
                )
                        - 1
        ) <= precision;
    }
}

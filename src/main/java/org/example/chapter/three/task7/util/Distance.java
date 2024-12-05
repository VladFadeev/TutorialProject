package org.example.chapter.three.task7.util;

import org.example.chapter.three.task7.entity.Point;
import org.example.chapter.three.task7.entity.RationalFraction;

public class Distance {
    public static double distanceBetween(Point p1, Point p2) {
        RationalFraction dx = p2.x().subtract(p1.x());
        RationalFraction dy = p2.y().subtract(p1.y());
        RationalFraction dz = p2.z().subtract(p1.z());
        RationalFraction distanceSquared = dx.multiply(dx).add(dy.multiply(dy)).add(dz.multiply(dz));
        return Math.sqrt((double) distanceSquared.getNumerator() / distanceSquared.getDenominator());
    }

    public static double distanceTo(Point p1) {
        RationalFraction dx = p1.x();
        RationalFraction dy = p1.y();
        RationalFraction dz = p1.z();
        RationalFraction distanceSquared = dx.multiply(dx).add(dy.multiply(dy)).add(dz.multiply(dz));
        return Math.sqrt((double) distanceSquared.getNumerator() / distanceSquared.getDenominator());
    }
}

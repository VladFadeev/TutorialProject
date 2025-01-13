package org.example.chapter.three.task7.service;

import org.example.chapter.three.task7.entity.RationalFraction;

public class Distance {
    /***
     * Calculates Euclidean distance based on differences in coordinates
     *
     * @param dx difference in x coordinate
     * @param dy difference in y coordinate
     * @param dz difference in z coordinate
     * @return distance based on differences in coordinates
     */
    public static double euclideanDistance(RationalFraction dx, RationalFraction dy, RationalFraction dz) {
        RationalFraction distanceSquared = dx.multiply(dx).add(dy.multiply(dy)).add(dz.multiply(dz));
        return Math.sqrt((double) distanceSquared.getNumerator() / distanceSquared.getDenominator());
    }
}

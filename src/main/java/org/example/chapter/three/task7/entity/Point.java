package org.example.chapter.three.task7.entity;

import org.example.chapter.three.task7.service.Distance;

import java.util.Objects;
public record Point(RationalFraction x,
                    RationalFraction y,
                    RationalFraction z) {

    /***
     * Creates Point from vector's start and end points
     *
     * @param start vector point
     * @param end vector point
     */
    public Point(Point start, Point end) {
        this(end.x().subtract(start.x()),
                end.y().subtract(start.y()),
                end.z().subtract(start.z()));
    }

    @Override
    public RationalFraction x() {
        return x.clone();
    }

    @Override
    public RationalFraction y() {
        return y.clone();
    }

    @Override
    public RationalFraction z() {
        return z.clone();
    }

    /***
     * Calculates Euclidean distance to other Point
     *
     * @param other point to which distance is calculated
     * @return distance between this point and other point
     */
    public double distanceTo(Point other) {
        RationalFraction dx = x().subtract(other.x());
        RationalFraction dy = y().subtract(other.y());
        RationalFraction dz = z().subtract(other.z());
        return Distance.euclideanDistance(dx, dy, dz);
    }

    /***
     * Calculates Euclidean distance from Origin
     *
     * @return distance from origin
     */
    public double distanceFromOrigin() {
        RationalFraction dx = x();
        RationalFraction dy = y();
        RationalFraction dz = z();
        return Distance.euclideanDistance(dx, dy, dz);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point point)) return false;
        return Objects.equals(x, point.x) && Objects.equals(y, point.y) && Objects.equals(z, point.z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "Point{" +
                x + ", " +
                y + ", " +
                z + '}';
    }
}

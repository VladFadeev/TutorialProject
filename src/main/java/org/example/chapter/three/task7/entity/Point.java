package org.example.chapter.three.task7.entity;

import org.example.utils.TaskEntity;

import java.util.Objects;

public record Point(RationalFraction x, RationalFraction y, RationalFraction z) implements TaskEntity {

    public Point(Point start, Point end) {
        this(end.x().subtract(start.x()), end.y().subtract(start.y()), end.z().subtract(start.z()));
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
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}

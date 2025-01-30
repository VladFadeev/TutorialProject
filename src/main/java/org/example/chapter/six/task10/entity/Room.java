package org.example.chapter.six.task10.entity;

import java.util.Objects;

/**
 * Represents room in {@code Building}. It has
 * {@code area} in square meters and {@code renovationsCount} -
 * renovations counter.
 */
public class Room {
    private double area;
    private int renovationsCount = 0;

    /**
     * Default constructor for {@code Room}.
     *
     * @param area room's area in square meters.
     */
    public Room(double area) {
        this.area = area;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public int getRenovationsCount() {
        return renovationsCount;
    }

    /**
     * Increases {@code renovationsCount}.
     */
    public void renovate() {
        this.renovationsCount++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room room)) return false;
        return Double.compare(area, room.area) == 0 && renovationsCount == room.renovationsCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(area, renovationsCount);
    }

    @Override
    public String toString() {
        return "Room{" +
                "area=" + area +
                ", renovationsCount=" + renovationsCount +
                '}';
    }
}


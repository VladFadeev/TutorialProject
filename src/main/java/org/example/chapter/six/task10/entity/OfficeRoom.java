package org.example.chapter.six.task10.entity;

import java.util.Objects;

public class OfficeRoom extends RentableRoom {
    private final int floor;

    /**
     * Default constructor for {@code OfficeRoom}.
     *
     * @param area room's area in square meters.
     * @param floor room's floor.
     */
    public OfficeRoom(int floor, double area) {
        super(area);
        this.floor = floor;
    }

    public int getFloor() {
        return floor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OfficeRoom that)) return false;
        if (!super.equals(o)) return false;
        return floor == that.floor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), floor);
    }
}

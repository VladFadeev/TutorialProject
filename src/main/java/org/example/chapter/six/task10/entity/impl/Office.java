package org.example.chapter.six.task10.entity.impl;

import org.example.chapter.six.task10.entity.Customer;
import org.example.chapter.six.task10.entity.OfficeRoom;
import org.example.chapter.six.task10.service.BuildingException;

import java.util.Objects;

/**
 * Represents Office in task 10 from chapter 6.
 * Extends {@code Shopping Center} and adds {@code floors}.
 */
public class Office extends ShoppingCenter {
    protected final int floors;

    /**
     * Default constructor for {@code Office}.
     *
     * @param price office's price.
     * @param areas area of each room in the office.
     */
    public Office(double price, double[] areas) {
        super(price);
        this.floors = areas.length;
        int floorNumber = 1;
        for (double area : areas) {
            this.rooms.add(new OfficeRoom(floorNumber++ % floors, area));
        }
    }

    /**
     * Full constructor for {@code Office}.
     *
     * @param price office's price.
     * @param areas area of each room in the office.
     * @param owner office's owner.
     */
    public Office(double price, double[] areas, Customer owner) {
        this(price, areas);
        this.owner = owner;
    }

    @Override
    public void increaseArea(int amount) throws BuildingException {
        throw new BuildingException("Illegal action. Cannot increase area of the office.");
    }

    public int getFloors() {
        return floors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Office office)) return false;
        return floors == office.floors;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(floors);
    }

    @Override
    public String toString() {
        return "Office{" +
                "floors=" + floors +
                ", customers=" + customers +
                ", price=" + price +
                ", rooms=" + rooms +
                ", owner=" + owner +
                '}';
    }
}

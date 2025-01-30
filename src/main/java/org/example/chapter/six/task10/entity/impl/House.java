package org.example.chapter.six.task10.entity.impl;

import org.example.chapter.six.task10.entity.Building;
import org.example.chapter.six.task10.entity.Customer;
import org.example.chapter.six.task10.entity.Room;
import org.example.chapter.six.task10.service.BuildingException;

import java.util.Objects;

/**
 * Represents House in task 10 from chapter 6.
 * Extends {@code Building} and adds {@code tenant}.
 */
public class House extends Building {
    private Customer tenant;

    /**
     * Default constructor for {@code House}.
     *
     * @param price house's price.
     * @param areas area of each room in the house.
     */
    public House(double price, double[] areas) {
        super(price, areas);
    }

    /**
     * Full constructor for {@code House}.
     *
     * @param price house's price.
     * @param areas area of each room in the house.
     * @param owner house's owner.
     */
    public House(double price, double[] areas, Customer owner) {
        super(price, areas, owner);
    }

    public void setTenant(Customer tenant) {
        this.tenant = tenant;
    }

    public Customer getTenant() {
        return tenant;
    }

    @Override
    public void increaseArea(int amount) throws BuildingException {
        rooms.add(new Room(amount));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof House house)) return false;
        return Objects.equals(tenant, house.tenant);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(tenant);
    }

    @Override
    public String toString() {
        return "House{" +
                "tenant=" + tenant +
                ", price=" + price +
                ", rooms=" + rooms +
                ", owner=" + owner +
                '}';
    }
}

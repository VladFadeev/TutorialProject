package org.example.chapter.six.task10.entity.impl;

import org.example.chapter.six.task10.entity.Building;
import org.example.chapter.six.task10.entity.Customer;
import org.example.chapter.six.task10.entity.RentableRoom;
import org.example.chapter.six.task10.service.BuildingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents Shopping Center in task 10 from chapter 6.
 * Extends {@code Building} and adds list of {@code customers}.
 */
public class ShoppingCenter extends Building {
    protected final List<Customer> customers = new ArrayList<>();

    /**
     * Primitive constructor for {@code ShoppingCenter}.
     *
     * @param price building's price.
     */
    public ShoppingCenter(double price) {
        super(price);
    }

    /**
     * Default constructor for {@code ShoppingCenter}.
     *
     * @param price shopping center's price.
     * @param areas area of each room in the shopping center.
     */
    public ShoppingCenter(double price, double[] areas) {
        this(price);
        for (double area : areas) {
            rooms.add(new RentableRoom(area));
        }
    }

    /**
     * Full constructor for {@code Shopping center}.
     *
     * @param price shopping center's price.
     * @param areas area of each room in the shopping center.
     * @param owner shopping center's owner.
     */
    public ShoppingCenter(double price, double[] areas, Customer owner) {
        this(price, areas);
        this.owner = owner;
    }

    @Override
    public void increaseArea(int amount) throws BuildingException {
        throw new BuildingException("Illegal action. Cannot increase area of the shopping center.");
    }

    public boolean isRoomAvailable(int roomNumber) {
        return ((RentableRoom) rooms.get(roomNumber)).isAvailable();
    }

    public List<Customer> getCustomers() {
        return List.copyOf(customers);
    }

    public void addCustomer(Customer customer, int roomNumber) {
        RentableRoom rentableRoom = (RentableRoom) rooms.get(roomNumber);
        if (rentableRoom.isAvailable()) {
            customers.add(customer);
            rentableRoom.setTenantNumber(customers.size());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShoppingCenter that)) return false;
        return Objects.equals(customers, that.customers);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(customers);
    }

    @Override
    public String toString() {
        return "ShoppingCenter{" +
                "customers=" + customers +
                ", price=" + price +
                ", rooms=" + rooms +
                ", owner=" + owner +
                '}';
    }
}

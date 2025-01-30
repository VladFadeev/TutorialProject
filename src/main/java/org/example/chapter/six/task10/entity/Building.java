package org.example.chapter.six.task10.entity;

import org.example.chapter.six.task10.service.BuildingException;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents Building for task 10 from chapter 6.
 * Each building has {@code price}, list of {@code rooms}
 * and {@code owner}.
 */
public abstract class Building {
    protected final double price;
    protected List<Room> rooms = new ArrayList<>();
    protected Customer owner;

    /**
     * Primitive constructor for {@code Building}.
     *
     * @param price building's price.
     */
    protected Building(double price) {
        this.price = price;
    }

    /**
     * Default constructor for {@code Building}.
     *
     * @param price building's price.
     * @param areas area of each room in the building.
     */
    protected Building(double price, double[] areas) {
        this(price);
        for (double area : areas) {
            rooms.add(new Room(area));
        }
    }

    /**
     * Full constructor for {@code Building}.
     *
     * @param price building's price.
     * @param areas area of each room in the building.
     * @param owner building's owner.
     */
    protected Building(double price, double[] areas, Customer owner) {
        this(price, areas);
        this.owner = owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public Customer getOwner() {
        return owner;
    }

    public double getPrice() {
        return price;
    }

    /**
     * Returns area of the specified room.
     * 
     * @param roomNumber room's number.
     * @return area of that room.
     * @see #getArea() for full area of the Building.
     */
    public double getRoomArea(int roomNumber) {
        return rooms.get(roomNumber - 1).getArea();
    }

    /**
     * Returns full area of the building.
     * 
     * @return full area of the building.
     * @see #getRoomArea(int) for area of one room.
     */
    public double getArea() {
        return rooms.stream().mapToDouble(Room::getArea).sum();
    }

    public abstract void increaseArea(int amount) throws BuildingException;

    public int getRoomCount() {
        return rooms.size();
    }

    public void renovateRoom(int roomNumber) {
        rooms.get(roomNumber - 1).renovate();
    }
}

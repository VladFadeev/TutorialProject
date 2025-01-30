package org.example.chapter.six.task10.service;

import org.example.chapter.six.task10.entity.Building;
import org.example.chapter.six.task10.entity.Customer;

public interface BuildingService<T extends Building> {
    /**
     * Build new {@code Building}
     *
     * @param price building's price.
     * @param areas of each room in the building.
     * @return new building.
     */
    T build(double price, double[] areas);

    /**
     * Build new {@code Building}
     *
     * @param price building's price.
     * @param areas of each room in the building.
     * @param owner building's owner.
     * @return new building.
     */
    T build(double price, double[] areas, Customer owner);

    /**
     * Let {@code building} to {@code tenant}.
     *
     * @param building building to let.
     * @param tenant tenant in building.
     * @throws BuildingException if letting is not possible.
     */
    default void let(T building, Customer tenant) throws BuildingException {
        throw new BuildingException("Illegal action. Cannot let the whole Office.");
    }

    /**
     * Let room with {@code roomNumber} in {@code building} to {@code tenant}.
     *
     * @param building building in which room is rented.
     * @param tenant tenant in building.
     * @param roomNumber number of room to let.
     * @throws BuildingException if letting is not possible.
     */
    default void let(T building, Customer tenant, int roomNumber) throws BuildingException {
        throw new BuildingException("Illegal action. Cannot let one room.");
    }

    default double getPricePerM2(T building) {
        return building.getPrice()/building.getArea();
    }

    default int getRoomCount(T building) {
        return building.getRoomCount();
    }

    default void increaseArea(T building, int amount) throws BuildingException {
        building.increaseArea(amount);
    }

    default void sell(T building, Customer owner) {
        building.setOwner(owner);
    }

    default void doRenovations(T building, int roomNumber) {
        building.renovateRoom(roomNumber);
    }
}

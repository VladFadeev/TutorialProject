package org.example.chapter.six.task10.service.impl;

import org.example.chapter.six.task10.entity.Customer;
import org.example.chapter.six.task10.entity.impl.House;
import org.example.chapter.six.task10.service.BuildingException;
import org.example.chapter.six.task10.service.BuildingService;

public class HouseService implements BuildingService<House> {
    @Override
    public House build(double price, double[] areas) {
        return new House(price, areas);
    }

    @Override
    public House build(double price, double[] areas, Customer owner) {
        return new House(price, areas, owner);
    }

    @Override
    public void let(House building, Customer tenant) throws BuildingException {
        building.setTenant(tenant);
    }
}

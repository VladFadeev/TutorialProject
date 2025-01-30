package org.example.chapter.six.task10.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.chapter.six.task10.entity.Customer;
import org.example.chapter.six.task10.entity.impl.Office;
import org.example.chapter.six.task10.service.BuildingException;
import org.example.chapter.six.task10.service.BuildingService;

public class OfficeService implements BuildingService<Office> {
    private  final static Logger LOGGER = LogManager.getLogger();

    @Override
    public Office build(double price, double[] areas) {
        return new Office(price, areas);
    }

    @Override
    public Office build(double price, double[] areas, Customer owner) {
        return new Office(price, areas, owner);
    }

    @Override
    public void let(Office building, Customer tenant, int roomNumber) throws BuildingException {
        if (building.isRoomAvailable(roomNumber)) {
            building.addCustomer(tenant, roomNumber);
        } else {
            LOGGER.error("Illegal action. Office room is already rented.");
            throw new BuildingException("Illegal action. Office room is already rented.");
        }
    }
}

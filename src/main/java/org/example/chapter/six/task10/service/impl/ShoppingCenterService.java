package org.example.chapter.six.task10.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.chapter.six.task10.entity.Customer;
import org.example.chapter.six.task10.entity.impl.ShoppingCenter;
import org.example.chapter.six.task10.service.BuildingException;
import org.example.chapter.six.task10.service.BuildingService;

public class ShoppingCenterService implements BuildingService<ShoppingCenter> {
    private  final static Logger LOGGER = LogManager.getLogger();

    @Override
    public ShoppingCenter build(double price, double[] areas) {
        return new ShoppingCenter(price, areas);
    }

    @Override
    public ShoppingCenter build(double price, double[] areas, Customer owner) {
        return new ShoppingCenter(price, areas, owner);
    }

    @Override
    public void let(ShoppingCenter building, Customer tenant, int roomNumber) throws BuildingException {
        if (building.isRoomAvailable(roomNumber)) {
            building.addCustomer(tenant, roomNumber);
        } else {
            LOGGER.error("Illegal action. Room is already rented.");
            throw new BuildingException("Illegal action. Room is already rented.");
        }
    }
}

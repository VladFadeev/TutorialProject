package org.example.chapter.six.task10;

import org.example.chapter.six.task10.entity.Customer;
import org.example.chapter.six.task10.entity.impl.House;
import org.example.chapter.six.task10.service.BuildingException;
import org.example.chapter.six.task10.service.BuildingService;
import org.example.chapter.six.task10.service.impl.HouseService;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class HouseServiceTest {
    private final BuildingService<House> buildingService = new HouseService();
    private House house;
    private Customer tenant;

    @BeforeClass
    private void setup() {
        house = new House(10, new double[]{15, 35});
        tenant = new Customer("Tenant", 25);
    }

    @Test
    public void testLet() {
        buildingService.let(house, tenant);
        Assert.assertEquals(house.getTenant(), tenant);
    }

    @Test(expectedExceptions = BuildingException.class)
    public void testLetRoom() {
        buildingService.let(house, tenant, 1);
    }

    @Test
    public void testIncreaseArea() {
        buildingService.increaseArea(house, 10);
        Assert.assertEquals(house.getArea() + house.getRoomCount(), 63);
    }

    @AfterClass
    private void teardown() {
        house = null;
        tenant = null;
    }
}

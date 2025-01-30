package org.example.chapter.six.task10;

import org.example.chapter.six.task10.entity.Customer;
import org.example.chapter.six.task10.entity.impl.ShoppingCenter;
import org.example.chapter.six.task10.service.BuildingException;
import org.example.chapter.six.task10.service.BuildingService;
import org.example.chapter.six.task10.service.impl.ShoppingCenterService;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ShoppingCenterServiceTest {
    BuildingService<ShoppingCenter> buildingService = new ShoppingCenterService();
    private ShoppingCenter shoppingCenter;
    private Customer tenant;

    @BeforeClass
    private void setup() {
        shoppingCenter = new ShoppingCenter(10, new double[]{15, 35});
        tenant = new Customer("Tenant", 25);
    }

    @Test(expectedExceptions = BuildingException.class)
    public void testLet() {
        buildingService.let(shoppingCenter, tenant);
    }

    @Test
    public void testLetRoom() {
        buildingService.let(shoppingCenter, tenant, 1);
        Assert.assertEquals(shoppingCenter.getCustomers().getFirst(), tenant);
    }

    @Test(expectedExceptions = BuildingException.class)
    public void testIncreaseArea() {
        buildingService.increaseArea(shoppingCenter, 10);
    }

    @AfterClass
    private void teardown() {
        shoppingCenter = null;
        tenant = null;
    }
}

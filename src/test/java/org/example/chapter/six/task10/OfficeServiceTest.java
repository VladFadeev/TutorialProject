package org.example.chapter.six.task10;

import org.example.chapter.six.task10.entity.Customer;
import org.example.chapter.six.task10.entity.impl.Office;
import org.example.chapter.six.task10.service.BuildingException;
import org.example.chapter.six.task10.service.BuildingService;
import org.example.chapter.six.task10.service.impl.OfficeService;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class OfficeServiceTest {
    BuildingService<Office> buildingService = new OfficeService();
    private Office office;
    private Customer tenant;

    @BeforeClass
    private void setup() {
        office = new Office(10, new double[]{15, 35});
        tenant = new Customer("Tenant", 25);
    }

    @Test(expectedExceptions = BuildingException.class)
    public void testLet() {
        buildingService.let(office, tenant);
    }

    @Test
    public void testLetRoom() {
        buildingService.let(office, tenant, 1);
        Assert.assertEquals(office.getCustomers().getFirst(), tenant);
    }

    @Test(expectedExceptions = BuildingException.class)
    public void testIncreaseArea() {
        buildingService.increaseArea(office, 10);
    }

    @AfterClass
    private void teardown() {
        office = null;
        tenant = null;
    }
}

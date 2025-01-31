package org.example.chapter.seven.task25;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class FunctionalServiceTest {
    @DataProvider(name = "strings")
    public Object[][] strings() {
        return new Object[][] {
                {"qazwsxedcrf 234;'[],/ vtgbyhnujmikolp", true},
                {"test", false},
                {"qwe345 rty (uiopasdzxcfghvbnjklm", true}};
    }

    @Test(dataProvider = "strings")
    public void testFunctionalService(String input, boolean expected) {
        boolean actual = FunctionalService.isPangram(input);
        Assert.assertEquals(actual, expected);
    }
}

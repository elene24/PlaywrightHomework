package ge.tbc.testautomation.test;

import ge.tbc.testautomation.runners.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;

public class E2Etests extends BaseTest {


    @Test(description = "Add random product to cart and modify quantity")
    @Description("This test adds a random product to the cart, modifies its quantity, and validates the cart")
    @Severity(SeverityLevel.CRITICAL)
    public void modifiyingcart(){
        magentosteps.add_random_product2Cart();
    }



}

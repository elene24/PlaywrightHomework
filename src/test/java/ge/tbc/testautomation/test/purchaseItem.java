package ge.tbc.testautomation.test;

import ge.tbc.testautomation.runners.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;

public class purchaseItem extends BaseTest {

    @Test(description = "Scenario: Add product to cart and complete checkout")
    @Description("This test adds a product to the cart, modifies quantity, checks out, and validates order success")
    @Severity(SeverityLevel.CRITICAL)
    public void AddToCartTest(){
        //mainpagetest.saveToFavsUnauthorized();
        magentosteps.purchaseItem();
        System.out.println("BranchA");

    }

}

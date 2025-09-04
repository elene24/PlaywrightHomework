package ge.tbc.testautomation.test;

import ge.tbc.testautomation.runners.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;

public class mobileNavigationTest extends BaseTest {


    @Test(description = "Validate mobile navigation menu links")
    @Description("Resizes browser to mobile viewport, opens burger menu, validates Account and Menu links")
    @Severity(SeverityLevel.CRITICAL)
    public void mobileNavigationTest(){
//        magentosteps.resize();
//        magentosteps.openBurgerMenu();
//        magentosteps.validateSignInandCreateAccount();
//        magentosteps.validateClothingTypes();
        //magentosteps.mobileNavigationTest();

        magentosteps.resizeToMobile();
        magentosteps.openBurgerMenu();
        magentosteps.validateAccountLinks();
        magentosteps.validateMainMenuLinks();

    }
}

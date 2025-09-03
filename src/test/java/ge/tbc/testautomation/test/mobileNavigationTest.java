package ge.tbc.testautomation.test;

import ge.tbc.testautomation.runners.BaseTest;
import org.testng.annotations.Test;

public class mobileNavigationTest extends BaseTest {

    @Test
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

package ge.tbc.testautomation.test;

import ge.tbc.testautomation.runners.BaseTest;
import org.testng.annotations.Test;

public class purchaseItem extends BaseTest {

    @Test
    public void AddToCartTest(){
        //mainpagetest.saveToFavsUnauthorized();
        magentosteps.purchaseItem();

    }

}

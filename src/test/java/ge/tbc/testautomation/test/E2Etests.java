package ge.tbc.testautomation.test;

import ge.tbc.testautomation.runners.BaseTest;
import org.testng.annotations.Test;

public class E2Etests extends BaseTest {

    @Test
    public void modifiyingcart(){
        magentosteps.add_random_product2Cart();
    }



}

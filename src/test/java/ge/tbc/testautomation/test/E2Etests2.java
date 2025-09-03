package ge.tbc.testautomation.test;

import ge.tbc.testautomation.runners.BaseTest;
import org.testng.annotations.Test;

public class E2Etests2 extends BaseTest {

    @Test
    public void writeAReview(){
        magentosteps.findRandomOffer();
        itemspagesteps.add_a_review();
    }
}

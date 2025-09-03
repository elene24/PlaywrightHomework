package ge.tbc.testautomation.test;

import ge.tbc.testautomation.runners.BaseTest;
import org.testng.annotations.Test;

public class ReviewNumberTest extends BaseTest {

    @Test
    public void reviewNumberTest(){
        magentosteps.findRandomOffer();
        itemspagesteps.ValidateReviewNumber(constants.Reviews_String);
    }
}

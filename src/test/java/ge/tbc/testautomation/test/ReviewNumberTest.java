package ge.tbc.testautomation.test;

import ge.tbc.testautomation.runners.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;

public class ReviewNumberTest extends BaseTest {

    @Test(description ="Validate review count of a random product")
    @Description("This test checks if the displayed review count matches the actual number of reviews in the list")
    @Severity(SeverityLevel.MINOR)    public void reviewNumberTest(){
        magentosteps.findRandomOffer();
        itemspagesteps.ValidateReviewNumber(constants.Reviews_String);
        System.out.println("Allure conflict");

    }
}

package ge.tbc.testautomation.test;

import ge.tbc.testautomation.runners.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;

public class E2Etests2 extends BaseTest {

    @Test(description = "Write a review for a random product")
    @Description("This test finds a random offer and submits a review for it")
    @Severity(SeverityLevel.NORMAL)
    public void writeAReview(){
        magentosteps.findRandomOffer();
        itemspagesteps.add_a_review();
    }
}

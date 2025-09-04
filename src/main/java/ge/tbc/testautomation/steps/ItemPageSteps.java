package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.pages.ItemPage;
import ge.tbc.testautomation.pages.MagentoHomePage;
import io.qameta.allure.Step;

public class ItemPageSteps {
    Page page;
        ItemPage itempage;
        Constants constants;

    public ItemPageSteps(Page page) {
        this.page = page;
        this.itempage= new ItemPage(page);
        this.constants = new Constants();
    }

    @Step("Open reviews section with tab")
    public void openReviewsSection(String input){
        itempage.reviews.getByText(input).click();
    }
    @Step("Validate review count matches displayed number input")
    public void ValidateReviewNumber(String input){
        Integer num = Integer.valueOf(itempage.review_title.first().textContent());
        System.out.println(num);
        openReviewsSection(input);
        PlaywrightAssertions.assertThat(itempage.review_list).hasCount(num);
    }


    @Step("Add a review for the product")
    public void add_a_review(){
        openReviewsSection(constants.Reviews_String);
        write_and_submit_review();
    }

    @Step("Write review details and submit")
    public void write_and_submit_review(){
        itempage.three_stars.click();
        itempage.nickname.fill(constants.Nickname);
        itempage.summary.fill(constants.Review);
        itempage.review.fill(constants.Review);
        itempage.submit_review.click();
    }
}

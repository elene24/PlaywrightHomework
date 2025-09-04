package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ItemPage {

    public Locator review_title;
    public Locator reviews;
    public Locator review_list;
    public Locator three_stars;
    public Locator nickname;
    public Locator summary;
    public Locator review;
    public Locator submit_review;


    public ItemPage(Page page) {
        this.review_title = page.locator("//a[@class='action view']//span");
        this.reviews = page.locator("//a[@class='data switch']");
        this.review_list = page.locator("//li[@class='item review-item']");
        this.three_stars = page.locator("//label[@title='3 stars']");
        this.nickname = page.locator("//input[@name='nickname']");
        this.summary = page.locator("//input[@name='title']");
        this.review = page.locator("//input[@name='detail']");
        this.submit_review = page.locator("//button[@class='action submit primary']");





    }
}

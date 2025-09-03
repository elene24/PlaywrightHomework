package ge.tbc.testautomation.test;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.Arrays;

public class PlayWrightIntro {
    Playwright playwright;
    Browser browser;
    BrowserContext browserContext;
    Page page;

    @BeforeClass
    public void setUp() {
        playwright = Playwright.create();
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        launchOptions.setArgs(Arrays.asList("--no-sandbox", "--disable-gpu", "--disable-extensions"));
        launchOptions.setHeadless(false);
        launchOptions.setSlowMo(1000);
        launchOptions.setTimeout(50000);
        browser = playwright.chromium().launch(launchOptions);
        browserContext = browser.newContext();
        page = browserContext.newPage();
    }

    @BeforeMethod
    public void openPage() {
        browserContext = browser.newContext();
        page = browserContext.newPage();
        page.navigate("https://practicesoftwaretesting.com/");
    }

    @AfterMethod
    public void tearDown() {
        browserContext.close();
    }

    @AfterClass
    public void closeAll() {
        browser.close();
        playwright.close();
    }

    @Test(priority = 1)
    public void registerAccount() {
        //sign up
        page.click("[data-test='nav-sign-in']");
        page.click("[data-test='register-link']");
        page.fill("[id='first_name']", "William");
        page.fill("[id='last_name']", "Bowery");
        page.fill("[id='dob']", "2000-08-08");
        page.fill("[id='street']", "Cornelia Street");
        page.fill("[id='postal_code']", "0101");
        page.fill("[id='city']", "London");
        page.fill("[id='state']", "England");
        page.selectOption("[id='country']", "Georgia");
        page.fill("[id='phone']", "555959595");
        page.fill("[id='email']", "joe@example.com");
        page.fill("[id='password']", "StrongPassword8663$");
        page.click("[data-test='register-submit']");
        page.waitForURL("https://practicesoftwaretesting.com/auth/login");
    }



    @Test(priority = 3)
    public void addToFavourites() {

        // 1️⃣ Log in
        page.locator("[data-test='nav-sign-in']").click();
        page.locator("[id='email']").fill("joe@example.com");
        page.locator("[id='password']").fill("StrongPassword8663$");
        page.locator("[data-test='login-submit']").click();
        page.waitForURL("https://practicesoftwaretesting.com/account");

        // go to homepage
        page.locator("[class='navbar-brand']").click();
        page.waitForLoadState(LoadState.NETWORKIDLE);

        // first 3 products
        String[] productHrefs = {
                "/product/01K3RFN5QT299YE7VXFKRCPJMJ",
                "/product/01K3RFN5QXJ32HRNSR2A1GFKQ9",
                "/product/01K3RFN5QZYJ4YXE0QA52QNW82"
        };

        for (String href : productHrefs) {

            Locator product = page.locator("a[href='" + href + "']");
            product.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(60000));
            product.click();
            page.waitForLoadState(LoadState.NETWORKIDLE);

            //adding to favs
            Locator favBtn = page.locator("[data-test='add-to-favorites']");
            favBtn.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(60000));
            favBtn.click();

            page.goBack();
            page.waitForLoadState(LoadState.NETWORKIDLE);
        }

        //log out
        page.locator("[data-test='nav-menu']").click();
        page.locator("[data-test='nav-sign-out']").click();


        //log back in
        page.locator("[data-test='nav-sign-in']").click();
        page.locator("[id='email']").fill("joe@example.com");
        page.locator("[id='password']").fill("StrongPassword8663$");
        page.locator("[data-test='login-submit']").click();
        page.waitForURL("https://practicesoftwaretesting.com/account");

        //chekcing favourites
        page.locator("[data-test='nav-favorites']").click();
        page.waitForLoadState(LoadState.NETWORKIDLE);

        Locator favProducts = page.locator("div.card[data-test^='favorite-']");

        favProducts.first().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(60000));

        //assert the number of favs is 3
        PlaywrightAssertions.assertThat(favProducts).hasCount(3);
    }


    @Test(priority = 4)
    public void filterByCategoryTest() {
        //log in
        page.locator("[data-test='nav-sign-in']").click();
        page.locator("[id='email']").fill("joe@example.com");
        page.locator("[id='password']").fill("StrongPassword8663$");
        page.locator("[data-test='login-submit']").click();
        page.waitForURL("https://practicesoftwaretesting.com/account");

        //back to homepage
        page.locator("[class='navbar-brand']").click();
        page.waitForLoadState(LoadState.NETWORKIDLE);

        //loading filters
        Locator filtersContainer = page.locator("div.checkbox");
        filtersContainer.first().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(60000));

        //activate hammer filter
        Locator hammerLabel = page.locator("label:has-text('Hammer')");
        hammerLabel.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(60000));
        hammerLabel.click();

        //waiting4loading and counting
        page.waitForLoadState(LoadState.NETWORKIDLE);
        page.locator(".card").first().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(60000));
        int countHammer = page.locator(".card").count();

        //same for hand saw
        Locator handSawLabel = page.locator("label:has-text('Hand Saw')");
        handSawLabel.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(60000));

        hammerLabel.click();

        handSawLabel.click();
        page.waitForLoadState(LoadState.NETWORKIDLE);
        page.locator(".card").first().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(60000));
        int countHandSaw = page.locator(".card").count();

        hammerLabel.click();
        handSawLabel.click();
        page.waitForLoadState(LoadState.NETWORKIDLE);
        page.locator(".card").first().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(60000));
        int combinedCount = page.locator(".card").count();

        //in case there are commob cards for both
        Assert.assertTrue(combinedCount <= countHammer + countHandSaw);
    }






    @Test(priority = 5)
    public void removeFavouriteTest() {

        // log in
        page.locator("[data-test='nav-sign-in']").click();
        page.locator("[id='email']").fill("joe@example.com");
        page.locator("[id='password']").fill("StrongPassword8663$");
        page.locator("[data-test='login-submit']").click();
        page.waitForURL("https://practicesoftwaretesting.com/account");

        // got o favourites
        page.locator("[data-test='nav-favorites']").click();
        page.waitForLoadState(LoadState.NETWORKIDLE);

        // get the number of favourites
        Locator favProducts = page.locator("div.card[data-test^='favorite-']");
        favProducts.first().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(60000));
        int initialCount = favProducts.count();

        // remove first favourite
        favProducts.first().locator("button[data-test='delete']").click();

        page.waitForTimeout(1000);

        //assertion
        PlaywrightAssertions.assertThat(favProducts).hasCount(initialCount - 1);

        //log out
        page.locator("[data-test='nav-menu']").click();
        page.locator("[data-test='nav-sign-out']").click();

        // log in
        page.locator("[data-test='nav-sign-in']").click();
        page.locator("[id='email']").fill("joe@example.com");
        page.locator("[id='password']").fill("StrongPassword8663$");
        page.locator("[data-test='login-submit']").click();
        page.waitForURL("https://practicesoftwaretesting.com/account");

        // 8check count again
        page.locator("[data-test='nav-favorites']").click();
        page.waitForLoadState(LoadState.NETWORKIDLE);
        Locator updatedFavProducts = page.locator("div.card[data-test^='favorite-']");
        PlaywrightAssertions.assertThat(updatedFavProducts).hasCount(initialCount - 1);
    }

    @Test
    public void tagsTest() {

        // activate hands tool filter
        page.locator("[data-test='nav-categories']").click();
        page.locator("a[href='/category/hand-tools']").click();
        page.waitForLoadState(LoadState.NETWORKIDLE);

        // activate hammer filter
        Locator hammerLabel = page.locator("label:has-text('Hammer')");
        hammerLabel.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(60000));
       hammerLabel.click();

        // find thor hammer and vaalidate tags
        Locator thorHammerCard = page.locator("a[data-test='product-01K3RFN5R8GDBBEVR1X1TNQ6B5']");
        thorHammerCard.click();
        page.waitForLoadState(LoadState.NETWORKIDLE);
        Locator categoryTag = page.locator("span[aria-label='category']");
        Locator brandTag = page.locator("span[aria-label='brand']");
        PlaywrightAssertions.assertThat(categoryTag).hasText("Hammer");
        PlaywrightAssertions.assertThat(brandTag).hasText("ForgeFlex Tools");
    }


}
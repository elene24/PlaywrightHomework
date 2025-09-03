package ge.tbc.testautomation.test;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.testng.annotations.*;

import java.util.Arrays;

public class MultipleUsers {

    Playwright playwright;
    Browser browser;

    @BeforeClass
    public void setUp() {
        playwright = Playwright.create();
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        launchOptions.setArgs(Arrays.asList("--no-sandbox", "--disable-gpu", "--disable-extensions"));
        launchOptions.setHeadless(false);
        launchOptions.setSlowMo(500);
        launchOptions.setTimeout(50000);
        browser = playwright.chromium().launch(launchOptions);
    }

    @AfterClass
    public void tearDownAll() {
        browser.close();
        playwright.close();
    }

    private Page register() {
        // new context
        BrowserContext context = browser.newContext();
        Page page = context.newPage();

        page.navigate("https://practicesoftwaretesting.com/");
        page.locator("[data-test='nav-sign-in']").click();
        page.locator("[data-test='register-link']").click();

        // unique email
        String uniqueEmail = "user" + System.currentTimeMillis() + "@example.com";

        page.fill("[id='first_name']", "William");
        page.fill("[id='last_name']", "Bowery");
        page.fill("[id='dob']", "2000-01-01");
        page.fill("[id='street']", "Cornelia Street");
        page.fill("[id='postal_code']", "0101");
        page.fill("[id='city']", "London");
        page.fill("[id='state']", "England");
        page.selectOption("[id='country']", "Georgia");
        page.fill("[id='phone']", "555555555");
        page.fill("[id='email']", uniqueEmail);
        page.fill("[id='password']", "StrongPassword8663$");
        page.click("[data-test='register-submit']");
        page.waitForURL("https://practicesoftwaretesting.com/auth/login");

        // log in with the same unique email
        page.fill("[id='email']", uniqueEmail);
        page.fill("[id='password']", "StrongPassword8663$");
        page.click("[data-test='login-submit']");
        page.waitForURL("https://practicesoftwaretesting.com/account");

        return page;
    }


    @Test
    public void addToFavoritesIsolated() {
        Page page = register();


        page.locator("[class='navbar-brand']").click();
        page.waitForLoadState(LoadState.NETWORKIDLE);

        //add favourites
        Locator product = page.locator("a[data-test='product-01K3RFN5QT299YE7VXFKRCPJMJ']");
        product.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(30000));
        product.click();

        page.waitForLoadState(LoadState.NETWORKIDLE);
        page.locator("[data-test='add-to-favorites']").click();
        page.waitForSelector("[data-test='nav-favorites']", new Page.WaitForSelectorOptions().setTimeout(60000));
        page.locator("[data-test='nav-favorites']").click();
        page.waitForLoadState(LoadState.NETWORKIDLE);
        Locator favProducts = page.locator("div.card[data-test^='favorite-']");
        PlaywrightAssertions.assertThat(favProducts).hasCount(1);


        page.context().close();
    }

    @Test
    public void removeFavoriteIsolated() {
        Page page = register();

        //add favouties
        page.locator("[class='navbar-brand']").click();
        page.waitForLoadState(LoadState.NETWORKIDLE);

        Locator product = page.locator("a[data-test='product-01K3RFN5QT299YE7VXFKRCPJMJ']");
        product.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(30000));
        product.click();
        page.waitForLoadState(LoadState.NETWORKIDLE);
        page.locator("[data-test='add-to-favorites']").click();

        // remove the favorites
        page.locator("[data-test='nav-favorites']").click();
        page.waitForLoadState(LoadState.NETWORKIDLE);
        Locator favProducts = page.locator("div.card[data-test^='favorite-']");
        favProducts.first().locator("button[data-test='delete']").click();
        page.waitForTimeout(1000);
        PlaywrightAssertions.assertThat(favProducts).hasCount(0);

        page.context().close();
    }


    @Test
    public void filterByCategoryIsolated() {
        Page page = register();
        //homepage
        page.locator("[class='navbar-brand']").click();
        page.waitForLoadState(LoadState.NETWORKIDLE);

        //wait and then filter
        Locator filtersContainer = page.locator("div.checkbox");
        filtersContainer.first().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE).setTimeout(60000));
        //hammer filter
        Locator hammerLabel = page.locator("label:has-text('Hammer')");
        hammerLabel.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE).setTimeout(60000));
        hammerLabel.click();
        page.waitForLoadState(LoadState.NETWORKIDLE);

        //number of hammers
        int countHammer = page.locator(".card").count();

        //hand saw filter
        Locator handSawLabel = page.locator("label:has-text('Hand Saw')");
        handSawLabel.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE).setTimeout(60000));

        hammerLabel.click();
        handSawLabel.click();
        page.waitForLoadState(LoadState.NETWORKIDLE);

        int countHandsaw = page.locator(".card").count();
        hammerLabel.click();
        handSawLabel.click();
        page.waitForLoadState(LoadState.NETWORKIDLE);

        int combinedCount = page.locator(".card").count();
       // System.out.println(combinedCount);

        assert combinedCount <= countHammer + countHandsaw;

        page.context().close();
    }

    @Test
    public void tagsTestIsolated() {
        Page page = register();

        page.locator("[data-test='nav-categories']").click();
        page.locator("a[href='/category/hand-tools']").click();
        page.waitForLoadState(LoadState.NETWORKIDLE);

        //hammer filter
        Locator hammerlabel = page.locator("label:has-text('Hammer')");
        hammerlabel.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE).setTimeout(60000));
        hammerlabel.click();

        // thor hammer location
        Locator thorhammerCard = page.locator("a[data-test='product-01K3RFN5R8GDBBEVR1X1TNQ6B5']");
        thorhammerCard.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE).setTimeout(60000));
        thorhammerCard.click();

        page.waitForLoadState(LoadState.NETWORKIDLE);

        // tags validation
        Locator categoryTag = page.locator("span[aria-label='category']");
        Locator brandTag = page.locator("span[aria-label='brand']");

        PlaywrightAssertions.assertThat(categoryTag).hasText("Hammer");
        PlaywrightAssertions.assertThat(brandTag).hasText("ForgeFlex Tools");

        page.context().close();
    }

}

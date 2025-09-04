package ge.tbc.testautomation.test;

import com.microsoft.playwright.*;
import ge.tbc.testautomation.steps.Playwrightintro.HomePageSteps;
import org.testng.annotations.*;
import io.qameta.allure.*;

import java.util.Arrays;

@Epic("Playwright Automation")
@Feature("Multiple Users Scenarios")
public class MultipleUsers {

    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;
    HomePageSteps steps;

    @BeforeClass
    @Step("set up Playwright, browser, context and page")
    public void setUp() {
        playwright = Playwright.create();
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        launchOptions.setArgs(Arrays.asList("--no-sandbox", "--disable-gpu", "--disable-extensions"));
        launchOptions.setHeadless(false);
        launchOptions.setSlowMo(500);
        launchOptions.setTimeout(50000);
        browser = playwright.chromium().launch(launchOptions);

        context = browser.newContext();
        page = context.newPage();
        steps = new HomePageSteps(page);
    }

    @AfterClass
    @Step("close Playwright context and browser")
    public void tearDownAll() {
        context.close();
        browser.close();
        playwright.close();
    }

    @Step("Register an unique user and log in")
    private String registerUniqueUser() {
        String email = "user" + System.currentTimeMillis() + "@example.com";

        steps.registerAccount("William", "Bowery", "2000-01-01",
                "Cornelia Street", "0101", "London", "England", "Georgia",
                "555555555", email, "StrongPassword8663$");

        steps.login(email, "StrongPassword8663$");
        return email;
    }

    @Test(description = "Add a product to favorites as a new user")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Favorites Management")
    public void addToFavoritesIsolated() {
        registerUniqueUser();

        String[] productHrefs = {"/product/01K3RFN5QT299YE7VXFKRCPJMJ"};
        steps.addProductsToFavorites(productHrefs);
        steps.verifyFavoritesCount(1);
    }

    @Test(description = "Remove a favorite product as a new user")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Favorites Management")
    public void removeFavoriteIsolated() {
        registerUniqueUser();

        String[] productHrefs = {"/product/01K3RFN5QT299YE7VXFKRCPJMJ"};
        steps.addProductsToFavorites(productHrefs);
        steps.removeFirstFavorite();
        steps.verifyFavoritesCount(0);
    }

    @Test(description = "filter products by category and verify counts")
    @Severity(SeverityLevel.NORMAL)
    @Story("Product Filters")
    public void filterByCategoryIsolated() {
        registerUniqueUser();

        int hammerCount = steps.applyHammerFilter();
        int sawCount = steps.applyHandSawFilter();
        steps.verifyCombinedFilterCount(hammerCount, sawCount);
    }

    @Test(description = "verify product tags on a product page")
    @Severity(SeverityLevel.NORMAL)
    @Story("Product Details")
    public void tagsTestIsolated() {
        registerUniqueUser();

        steps.verifyTags("01K3RFN5R8GDBBEVR1X1TNQ6B5", "Hammer", "ForgeFlex Tools");
    }
}

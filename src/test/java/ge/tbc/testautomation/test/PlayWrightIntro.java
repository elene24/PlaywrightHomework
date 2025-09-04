package ge.tbc.testautomation.test;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import ge.tbc.testautomation.steps.Playwrightintro.HomePageSteps;
import org.testng.Assert;
import org.testng.annotations.*;
import io.qameta.allure.*;

import java.util.Arrays;

@Epic("Playwright Intro Automation Tests")
@Feature("Practice Software Testing Website")
public class PlayWrightIntro {
    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;
    HomePageSteps homeSteps;

    @BeforeClass
    @Step("Set up Playwright and browser context")
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setArgs(Arrays.asList("--no-sandbox", "--disable-gpu")));
        context = browser.newContext();
        page = context.newPage();
        homeSteps = new HomePageSteps(page);
    }

    @BeforeMethod
    @Step("Open main page")
    public void openPage() {
        page.navigate("https://practicesoftwaretesting.com/");
    }

    @AfterClass
    @Step("Close browser and Playwright")
    public void tearDown() {
        browser.close();
        playwright.close();
    }

    @Test(priority = 1, description = "Register a new user account")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Registers a new account for user William Bowery with valid details")
    public void registerAccountTest() {
        homeSteps.registerAccount("William", "Bowery", "2000-08-08",
                "Cornelia Street", "0101", "London", "England", "Georgia",
                "555959595", "joe@example.com", "StrongPassword8663$");
    }

    @Test(priority = 2, description = "Add products to favorites and verify count")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Logs in, adds multiple products to favorites, logs out and back in, verifies favorites count")
    public void addToFavoritesTest() {
        homeSteps.login("joe@example.com", "StrongPassword8663$");
        String[] products = {
                "/product/01K3RFN5QT299YE7VXFKRCPJMJ",
                "/product/01K3RFN5QXJ32HRNSR2A1GFKQ9",
                "/product/01K3RFN5QZYJ4YXE0QA52QNW82"
        };
        homeSteps.addProductsToFavorites(products);
        homeSteps.logout();
        homeSteps.login("joe@example.com", "StrongPassword8663$");
        homeSteps.verifyFavoritesCount(3);
    }

    @Test(priority = 3, description = "Filter products by category and verify combined count")
    @Severity(SeverityLevel.NORMAL)
    @Description("Applies hammer and hand saw filters and verifies combined product count does not exceed sum of individual counts")
    public void filterByCategoryTest() {
        homeSteps.login("joe@example.com", "StrongPassword8663$");
        int hammerCount = homeSteps.applyHammerFilter();
        int sawCount = homeSteps.applyHandSawFilter();
        homeSteps.verifyCombinedFilterCount(hammerCount, sawCount);
    }

    @Test(priority = 4, description = "Remove a product from favorites and verify count")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Removes first favorite product, logs out and logs back in, verifies updated favorites count")
    public void removeFavoriteTest() {
        homeSteps.login("joe@example.com", "StrongPassword8663$");
        homeSteps.removeFirstFavorite();
        homeSteps.logout();
        homeSteps.login("joe@example.com", "StrongPassword8663$");
        homeSteps.verifyFavoritesCount(2);
    }

    @Test(priority = 5, description = "Verify product tags")
    @Severity(SeverityLevel.MINOR)
    @Description("Navigates to product page and validates its category and brand tags")
    public void tagsTest() {
        homeSteps.verifyTags("01K3RFN5R8GDBBEVR1X1TNQ6B5", "Hammer", "ForgeFlex Tools");
    }

}

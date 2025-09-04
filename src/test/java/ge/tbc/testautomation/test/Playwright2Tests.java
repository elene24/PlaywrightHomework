package ge.tbc.testautomation.test;

import com.microsoft.playwright.*;
import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.steps.MainPageSteps;
import org.testng.annotations.*;
import io.qameta.allure.*;

import java.util.Arrays;

@Epic("Playwright Advanced Automation")
@Feature("Practice Software Testing Website - Main Page Tests")
public class Playwright2Tests {
    Playwright playwright;
    Browser browser;
    BrowserContext browserContext;
    Page page;
    Constants constants = new Constants();
    MainPageSteps mainpagesteps;

    @BeforeClass
    @Step("Set up Playwright and browser context")
    public void setUp(){
        playwright = Playwright.create();
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        launchOptions.setArgs(Arrays.asList("--no-sandbox", "--disable-gpu", "--disable-extensions"));
        launchOptions.setHeadless(false);
        launchOptions.setSlowMo(1000);
        launchOptions.setTimeout(50000);
        browser = playwright.chromium().launch(launchOptions);
        browserContext = browser.newContext();
        page = browserContext.newPage();
        mainpagesteps = new MainPageSteps(page);
    }

    @BeforeMethod
    @Step("Open main page")
    public void openPage(){
        page.navigate(constants.WebsiteURL);
    }

    @AfterClass
    @Step("Close browser and Playwright")
    public void tearDown(){
        browser.close();
        playwright.close();
    }

    @Test(description = "Validate color changes when selecting random product colors")
    @Severity(SeverityLevel.NORMAL)
    @Description("Selects 3 random products from hot-selling items and validates that the images change correctly for each color")
    public void ColorChangeTest(){
        mainpagesteps.validateColors(mainpagesteps.pickRandomItems());
    }

    @Test(description = "Add a random product to the cart and verify details")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Searches for products, adds a random product to the cart, opens mini-cart, and validates product name and price")
    public void AddToCartTest(){
        mainpagesteps.addRandomProductToCart();
    }

    @Test(description = "Validate out-of-stock message for unavailable products")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Opens first hot-selling item, selects size/color, tries to add to cart, validates out-of-stock message, and verifies cart remains empty")
    public void OutOfStock(){
        mainpagesteps.outOfStock();
    }

    @Test(description = "Attempt to save product to favorites while unauthorized and register user")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Tries to add product to favorites without logging in, verifies error, registers a new user, and validates welcome message")
    public void saveToFavoritesWhileUnauthorizedTest(){
        mainpagesteps.saveToFavsUnauthorized();
    }

    @Test(dependsOnMethods = {"AddToCartTest"}, description = "Delete the first item from the cart and verify removal")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Opens mini-cart, deletes the first product, and verifies that the product is removed")
    public void DeletefromCart(){
        mainpagesteps.deleteFromCart();
    }
}

package ge.tbc.testautomation.test;

import com.microsoft.playwright.*;
import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.steps.MainPageSteps;
import org.testng.annotations.*;

import java.util.Arrays;

public class Playwright2Tests {
    Playwright playwright;
    Browser browser;
    BrowserContext browserContext;
    Page page;
    Constants constants = new Constants();
    MainPageSteps mainpagesteps;

    @BeforeClass
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

        //errormessage



    }

    @BeforeMethod
    public void openPage(){
        page.navigate(constants.WebsiteURL);
    }


    @AfterClass
    public void tearDown(){
        browser.close();
        playwright.close();
    }


    @Test
    public void ColorChangeTest(){
        mainpagesteps.validateColors(mainpagesteps.pickRandomItems());
    }

    @Test
    public void AddToCartTest(){
        mainpagesteps.addRandomProductToCart();
    }

    @Test
    public void OutOfStock(){
        mainpagesteps.outOfStock();
    }


    @Test
    public void saveToFavoritesWhileUnauthorizedTest(){
        mainpagesteps.saveToFavsUnauthorized();
    }

    @Test(dependsOnMethods = {"AddToCartTest"})
    public void DeletefromCart(){
        mainpagesteps.deleteFromCart();
    }
}


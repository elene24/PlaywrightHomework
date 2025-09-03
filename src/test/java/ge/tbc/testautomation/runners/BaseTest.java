package ge.tbc.testautomation.runners;

import com.microsoft.playwright.*;
import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.steps.ItemPageSteps;
import ge.tbc.testautomation.steps.MagentoHomePageSteps;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.nio.file.Paths;
import java.util.Arrays;

import static ge.tbc.testautomation.data.Constants.EXTENSION_PATH;
import static ge.tbc.testautomation.data.Constants.TMP_PATH;

public class BaseTest {

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext browserContext;
    protected Page page;
    protected MagentoHomePageSteps magentosteps;
    protected ItemPageSteps itemspagesteps;
    protected Constants constants;

    @BeforeClass
    public void setUp() {
        constants = new Constants();
        playwright = Playwright.create();

        browserContext = playwright.chromium().launchPersistentContext(
                Paths.get(TMP_PATH),
                new BrowserType.LaunchPersistentContextOptions()
                        .setHeadless(false)
                        .setSlowMo(10000)
                        .setArgs(Arrays.asList(
                                String.format("--load-extensions=%s", EXTENSION_PATH),
                                String.format("--disable-extensions-except=%s", EXTENSION_PATH)
                        ))
        );

        page = browserContext.newPage();



        magentosteps = new MagentoHomePageSteps(page);
        itemspagesteps = new ItemPageSteps(page);

        page.navigate(constants.WebsiteURL, new Page.NavigateOptions().setTimeout(120_000));
    }

    @AfterClass
    public void tearDown() {
        if (browserContext != null) browserContext.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}

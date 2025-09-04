package ge.tbc.testautomation.steps.Playwrightintro;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import ge.tbc.testautomation.pages.Playwrightintro.HomePage;
import io.qameta.allure.Step;
import org.testng.Assert;

public class HomePageSteps {
    private final Page page;
    private final HomePage homePage;

    public HomePageSteps(Page page) {
        this.page = page;
        this.homePage = new HomePage(page);
    }

    @Step("Register a new user with email")
    public void registerAccount(String first, String last, String dob, String street,
                                String postal, String city, String state, String country,
                                String phone, String email, String password) {
        homePage.navSignIn.click();
        homePage.navRegister.click();
        homePage.firstNameField.fill(first);
        homePage.lastNameField.fill(last);
        homePage.dobField.fill(dob);
        homePage.streetField.fill(street);
        homePage.postalCodeField.fill(postal);
        homePage.cityField.fill(city);
        homePage.stateField.fill(state);
        homePage.countryDropdown.selectOption(country);
        homePage.phoneField.fill(phone);
        homePage.registerEmailField.fill(email);
        homePage.registerPasswordField.fill(password);
        homePage.registerSubmitBtn.click();
        page.waitForURL("**/auth/login");
    }

    @Step("Login with email")
    public void login(String email, String password) {
        homePage.navSignIn.click();
        homePage.emailField.fill(email);
        homePage.passwordField.fill(password);
        homePage.loginSubmitBtn.click();
        page.waitForURL("**/account");
    }

    @Step("Logout current user")
    public void logout() {
        homePage.navMenu.click();
        homePage.navSignOut.click();
    }

    @Step("Add products to favorites")
    public void addProductsToFavorites(String[] productHrefs) {
        homePage.logoBtn.click();
        page.waitForLoadState(LoadState.NETWORKIDLE);

        for (String href : productHrefs) {
            page.locator("a[href='" + href + "']").click();
            page.waitForLoadState(LoadState.NETWORKIDLE);
            homePage.addToFavoritesBtn.click();
            page.goBack();
            page.waitForLoadState(LoadState.NETWORKIDLE);
        }
    }

    @Step("Verify that favorites count equals expectedCount")
    public void verifyFavoritesCount(int expectedCount) {
        homePage.navFavorites.click();
        page.waitForLoadState(LoadState.NETWORKIDLE);
        PlaywrightAssertions.assertThat(homePage.favouriteProducts).hasCount(expectedCount);
    }

    @Step("Remove the first favorite product")
    public void removeFirstFavorite() {
        int initialCount = homePage.favouriteProducts.count();
        homePage.favouriteProducts.first().locator("button[data-test='delete']").click();
        page.waitForTimeout(1000);
        PlaywrightAssertions.assertThat(homePage.favouriteProducts).hasCount(initialCount - 1);
    }

    @Step("Apply Hammer filter")
    public int applyHammerFilter() {
        homePage.hammerFilter.click();
        page.waitForLoadState(LoadState.NETWORKIDLE);
        return homePage.allProductCards.count();
    }

    @Step("Apply Hand Saw filter")
    public int applyHandSawFilter() {
        homePage.handSawFilter.click();
        page.waitForLoadState(LoadState.NETWORKIDLE);
        return homePage.allProductCards.count();
    }

    @Step("Verify combined filter count (hammer adn saw)")
    public void verifyCombinedFilterCount(int hammerCount, int sawCount) {
        int combined = homePage.allProductCards.count();
        Assert.assertTrue(combined <= hammerCount + sawCount);
    }

    @Step("Verify tags of product")
    public void verifyTags(String productId, String expectedCategory, String expectedBrand) {
        homePage.navCategories.click();
        page.locator("a[href='/category/hand-tools']").click();
        page.waitForLoadState(LoadState.NETWORKIDLE);

        homePage.hammerFilter.click();
        page.locator("a[data-test='product-" + productId + "']").click();
        page.waitForLoadState(LoadState.NETWORKIDLE);

        PlaywrightAssertions.assertThat(homePage.categoryTag).hasText(expectedCategory);
        PlaywrightAssertions.assertThat(homePage.brandTag).hasText(expectedBrand);
    }
}

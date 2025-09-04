package ge.tbc.testautomation.pages.Playwrightintro;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class HomePage {
    public final Page page;

    public final Locator navSignIn;
    public final Locator navRegister;
    public final Locator navFavorites;
    public final Locator navCategories;
    public final Locator navMenu;
    public final Locator navSignOut;
    public final Locator logoBtn;

    public final Locator emailField;
    public final Locator passwordField;
    public final Locator loginSubmitBtn;
    public final Locator registerSubmitBtn;

    public final Locator firstNameField;
    public final Locator lastNameField;
    public final Locator dobField;
    public final Locator streetField;
    public final Locator postalCodeField;
    public final Locator cityField;
    public final Locator stateField;
    public final Locator countryDropdown;
    public final Locator phoneField;
    public final Locator registerEmailField;
    public final Locator registerPasswordField;

    public final Locator filtersContainer;
    public final Locator hammerFilter;
    public final Locator handSawFilter;

    public final Locator allProductCards;
    public final Locator addToFavoritesBtn;

    public final Locator favouriteProducts;
    public final Locator deleteFavouriteBtn;

    public final Locator categoryTag;
    public final Locator brandTag;

    public HomePage(Page page) {
        this.page = page;

        navSignIn = page.locator("[data-test='nav-sign-in']");
        navRegister = page.locator("[data-test='register-link']");
        navFavorites = page.locator("[data-test='nav-favorites']");
        navCategories = page.locator("[data-test='nav-categories']");
        navMenu = page.locator("[data-test='nav-menu']");
        navSignOut = page.locator("[data-test='nav-sign-out']");
        logoBtn = page.locator(".navbar-brand");

        emailField = page.locator("#email");
        passwordField = page.locator("#password");
        loginSubmitBtn = page.locator("[data-test='login-submit']");
        registerSubmitBtn = page.locator("[data-test='register-submit']");

        firstNameField = page.locator("#first_name");
        lastNameField = page.locator("#last_name");
        dobField = page.locator("#dob");
        streetField = page.locator("#street");
        postalCodeField = page.locator("#postal_code");
        cityField = page.locator("#city");
        stateField = page.locator("#state");
        countryDropdown = page.locator("#country");
        phoneField = page.locator("#phone");
        registerEmailField = page.locator("#email");
        registerPasswordField = page.locator("#password");
        filtersContainer = page.locator("div.checkbox");
        hammerFilter = page.locator("label:has-text('Hammer')");
        handSawFilter = page.locator("label:has-text('Hand Saw')");

        allProductCards = page.locator(".card");
        addToFavoritesBtn = page.locator("[data-test='add-to-favorites']");

        favouriteProducts = page.locator("div.card[data-test^='favorite-']");
        deleteFavouriteBtn = page.locator("button[data-test='delete']");


        categoryTag = page.locator("span[aria-label='category']");
        brandTag = page.locator("span[aria-label='brand']");
    }
}

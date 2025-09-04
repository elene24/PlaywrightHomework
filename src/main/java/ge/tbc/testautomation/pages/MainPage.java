package ge.tbc.testautomation.pages;


import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MainPage {

    public  Page page;

    public  Locator HOT_SELLING_ITEMS;
    public  Locator HOT_SELLING_ITEM_COLORS;

    public  Locator SIZE_SWATCH;
    public final Locator COLOR_SWATCH;

    public final Locator ADD_TO_CART_BUTTON;
    public final Locator PRODUCT_ADD_TO_CART_BUTTON;
    public final Locator ADD_TO_WISHLIST;
    public final Locator CREATE_ACCOUNT_BUTTON;
    public final Locator MINI_CART_BUTTON;
    public final Locator CONFIRM_REMOVE_BUTTON;

    public final Locator OUT_OF_STOCK_ERROR;
    public final Locator UNAUTHORIZED_WISHLIST_MESSAGE;
    public final Locator WISHLIST_SUCCESS_MESSAGE_CONTAINER;
    public final Locator WISHLIST_SUCCESS_MESSAGE;
    public final Locator WELCOME_MESSAGE;

    public final Locator CART_EMPTY_TEXT;
    public final Locator CART_ITEM_DETAILS;
    public final Locator CART_ITEM_NAME;
    public final Locator CART_ITEM_REMOVE_BUTTON;

    public final Locator PRODUCT_PRICE;
    public final Locator CART_PRICE;
    public final Locator PRODUCT_NAME;

    public final Locator SEARCH_INPUT;
    public final Locator SEARCH_BUTTON;

    public final Locator REG_FORM_CONTAINER;
    public final Locator FIRST_NAME_INPUT;
    public final Locator LAST_NAME_INPUT;
    public final Locator EMAIL_INPUT;
    public final Locator PASSWORD_INPUT;
    public final Locator PASSWORD_CONFIRM_INPUT;
    public final Locator CREATE_ACCOUNT_FORM_BUTTON;

    public static final String OUT_OF_STOCK_MESSAGE = "The requested qty is not available";
    public static final String UNAUTHORIZED_WISHLIST_TEXT = "You must login or register to add items to your wishlist.";
    public static final String WELCOME_TEXT_TEMPLATE = "Welcome, %s %s!";
    public static final String CART_EMPTY_TEXT_VALUE = "You have no items in your shopping cart.";

    public MainPage(Page page) {
        this.page = page;

        this.HOT_SELLING_ITEMS = page.locator("//div[@class='product-item-info']");
        this.HOT_SELLING_ITEM_COLORS = page.locator(".//div[@aria-label='Color']");

        SIZE_SWATCH = page.locator("//div[@class='swatch-option text']");
        COLOR_SWATCH = page.locator("//div[@class='swatch-option color']");

        ADD_TO_CART_BUTTON = page.locator("//button[@title='Add to Cart']");
        PRODUCT_ADD_TO_CART_BUTTON = page.locator("//button[@id='product-addtocart-button']");
        ADD_TO_WISHLIST = page.locator("//div[@class='product-addto-links']//a[@class='action towishlist']");
        CREATE_ACCOUNT_BUTTON = page.locator("//a[@class='action create primary']");
        MINI_CART_BUTTON = page.locator("//a[@class='action showcart']");
        CONFIRM_REMOVE_BUTTON = page.locator("//button[@class='action-primary action-accept']");

        OUT_OF_STOCK_ERROR = page.locator("//div[contains(@class,'message-error')]");
        UNAUTHORIZED_WISHLIST_MESSAGE = page.locator("//div[@role='alert']//div[contains(@class,'message-error')]");
        WISHLIST_SUCCESS_MESSAGE_CONTAINER = page.locator("div[role='alert'].messages");
        WISHLIST_SUCCESS_MESSAGE = page.locator(".//div[@class='message-success']");
        WELCOME_MESSAGE = page.locator("//span[@class='logged-in']");

        CART_EMPTY_TEXT = page.locator("//strong[@class='subtitle empty']");
        CART_ITEM_DETAILS = page.locator("//div[@class='product-item-details']");
        CART_ITEM_NAME = page.locator(".//strong[@class='product-item-name']//a");
        CART_ITEM_REMOVE_BUTTON = page.locator(".//a[@title='Remove item']");

        PRODUCT_PRICE = page.locator("//div[@class='product-info-main']//span[@class='price']");
        CART_PRICE = page.locator("//div[@id='ui-id-1']//span[contains(@class,'price-excluding-tax')]//span//span");
        PRODUCT_NAME = page.locator("//div[@class='product-info-main']//span[@class='base']");

        SEARCH_INPUT = page.locator("//input[@id='search']");
        SEARCH_BUTTON = page.locator("//button[@class='action search']");

        REG_FORM_CONTAINER = page.locator("//form[@id='form-validate']");
        FIRST_NAME_INPUT = page.locator("//input[@id='firstname']");
        LAST_NAME_INPUT = page.locator("//input[@id='lastname']");
        EMAIL_INPUT = page.locator("//input[@id='email_address']");
        PASSWORD_INPUT = page.locator("//input[@id='password']");
        PASSWORD_CONFIRM_INPUT = page.locator("//input[@id='password-confirmation']");
        CREATE_ACCOUNT_FORM_BUTTON = page.locator("//button[@title='Create an Account']");
    }
}

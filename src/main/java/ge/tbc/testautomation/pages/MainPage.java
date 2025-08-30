package ge.tbc.testautomation.pages;

public class MainPage {

    // --- Hot Selling Items ---
    public static final String HOT_SELLING_ITEMS = "//div[@class='product-item-info']";
    public static final String HOT_SELLING_ITEM_COLORS = ".//div[@aria-label='Color']";

    // --- Product options ---
    public static final String SIZE_SWATCH = "//div[@class='swatch-option text']";
    public static final String COLOR_SWATCH = "//div[@class='swatch-option color']";

    // --- Buttons ---
    public static final String ADD_TO_CART_BUTTON = "//button[@title='Add to Cart']";
    public static final String PRODUCT_ADD_TO_CART_BUTTON = "//button[@id='product-addtocart-button']";
    public static final String ADD_TO_WISHLIST = "//div[@class='product-addto-links']//a[@class='action towishlist']";
    public static final String CREATE_ACCOUNT_BUTTON = "//a[@class='action create primary']";
    public static final String MINI_CART_BUTTON = "//a[@class='action showcart']";
    public static final String CONFIRM_REMOVE_BUTTON = "//button[@class='action-primary action-accept']";

    // --- Alerts / Messages ---
    public static final String OUT_OF_STOCK_ERROR = "//div[contains(@class,'message-error')]";
    public static final String UNAUTHORIZED_WISHLIST_MESSAGE = "//div[@role='alert']//div[contains(@class,'message-error')]";
    public static final String WISHLIST_SUCCESS_MESSAGE_CONTAINER = "div[role='alert'].messages";
    public static final String WISHLIST_SUCCESS_MESSAGE = ".//div[@class='message-success']";
    public static final String WELCOME_MESSAGE = "//span[@class='logged-in']";

    // --- Cart ---
    public static final String CART_EMPTY_TEXT = "//strong[@class='subtitle empty']";
    public static final String CART_ITEM_DETAILS = "//div[@class='product-item-details']";
    public static final String CART_ITEM_NAME = ".//strong[@class='product-item-name']//a";
    public static final String CART_ITEM_REMOVE_BUTTON = ".//a[@title='Remove item']";

    // --- Product Price / Name ---
    public static final String PRODUCT_PRICE = "//div[@class='product-info-main']//span[@class='price']";
    public static final String CART_PRICE = "//div[@id='ui-id-1']//span[contains(@class,'price-excluding-tax')]//span//span";
    public static final String PRODUCT_NAME = "//div[@class='product-info-main']//span[@class='base']";

    // --- Search ---
    public static final String SEARCH_INPUT = "//input[@id='search']";
    public static final String SEARCH_BUTTON = "//button[@class='action search']";

    // --- Registration Form ---
    public static final String REG_FORM_CONTAINER = "//form[@id='form-validate']";
    public static final String FIRST_NAME_INPUT = "//input[@id='firstname']";
    public static final String LAST_NAME_INPUT = "//input[@id='lastname']";
    public static final String EMAIL_INPUT = "//input[@id='email_address']";
    public static final String PASSWORD_INPUT = "//input[@id='password']";
    public static final String PASSWORD_CONFIRM_INPUT = "//input[@id='password-confirmation']";
    public static final String CREATE_ACCOUNT_FORM_BUTTON = "//button[@title='Create an Account']";

    // --- Static text ---
    public static final String OUT_OF_STOCK_MESSAGE = "The requested qty is not available";
    public static final String UNAUTHORIZED_WISHLIST_TEXT = "You must login or register to add items to your wishlist.";
    public static final String WELCOME_TEXT_TEMPLATE = "Welcome, %s %s!";
    public static final String CART_EMPTY_TEXT_VALUE = "You have no items in your shopping cart.";
}

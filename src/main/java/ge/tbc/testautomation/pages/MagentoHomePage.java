package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MagentoHomePage {

    public Locator offers;
    public Locator offers_review;
    public Locator menu_toggle;
    public Locator menu_sections;
    public Locator acount_collapsibles;
    public Locator menu_collapsibles;
    public Locator add_to_cart;
    public Locator mini_cart;
    public Locator search_input;
    public Locator search_button;
    public Locator cart_item_name;
    public Locator product_name ;
    public Locator sign_in;
    public Locator email_input_signin;
    public Locator pass_input_signin;
    public Locator login_button;
    public Locator logged_in_menu_dropdown;
    public Locator favourites;
    public Locator items_in_wishlsit;
    public Locator add_all_to_cart;
    public Locator checkout_button;
    public Locator email;
    public Locator first_name;
    public Locator last_name;
    public Locator street;
    public Locator city;
    public Locator region;
    public Locator postcode;
    public Locator country;
    public Locator telephone;
    public Locator shipping;
    public Locator submit;
    public Locator shippinfo;
    public Locator discountinput;
    public Locator discount_submit;
    public Locator discount_message;
    public Locator discounttoggle;
    public Locator placeorder;
    public Locator ordernumber;
    public Locator cartedit;
    public Locator cart_quantity;
    public Locator quiantity_update;










    public MagentoHomePage(Page page) {
        this.offers = page.locator("//div[@class='product-item-info']");
        this.offers_review = page.locator("//div[@class='reviews-actions']");
        this.menu_toggle= page.locator("//span[@data-action='toggle-nav']");
        this.menu_sections= page.locator("//div[@data-role='collapsible']//a");
        this.acount_collapsibles = page.locator("//div[@class='section-item-content nav-sections-item-content']//ul[@class='header links']//li//a");
        this.menu_collapsibles = page.locator("//nav[@class='navigation']//a");
        this.add_to_cart = page.locator("//button[@title='Add to Cart']");
        this.mini_cart = page.locator("//a[@class='action showcart']");
        this.search_input = page.locator("//input[@id='search']");
        this.search_button = page.locator("//button[@class='action search']");

        this.cart_item_name= page.locator("//strong[@class='product-item-name']//a");
        this.product_name = page.locator("//h1//span[@data-ui-id='page-title-wrapper']");

        this.sign_in = page.locator("//ul[@class='header links']//li[@class='authorization-link']");
        this.email_input_signin = page.locator("//div[@class='field email required']//input[@id='email']");

        this.pass_input_signin = page.locator("//div[@class='field password required']//input[@id='pass']");
        this.login_button = page.locator("//button[@class='action login primary']");
        this.logged_in_menu_dropdown = page.locator("li.customer-welcome.active button.action.switch");
        this.favourites = page.locator("//li[@class='link wishlist']//a");
        this.items_in_wishlsit= page.locator("//li[@data-row='product-item']");
        this.add_all_to_cart = page.locator("//button[@data-role='all-tocart']");
        this.checkout_button = page.locator("//button[@class='action primary checkout']");
        this.first_name = page.locator("//input[@name='firstname']");
        this.email = page.locator("//input[@id='customer-email']");
        this.last_name = page.locator("//input[@name='lastname']");
        this.street = page.locator("//input[@name='street[0]']");
        this.city = page.locator("//input[@name='city']");
        this.region = page.locator("//select[@name='region_id']");
        this.country = page.locator("//select[@name='country_id']");
        this.postcode = page.locator("//input[@name='postcode']");
        this.telephone = page.locator("//input[@name='telephone']");
        this.shipping = page.locator("//input[@class='radio']");
        this.submit = page.locator("//button[@class='button action continue primary']");
   this.shippinfo= page.locator("div.shipping-information-content");
   this.discountinput = page.locator("//input[@placeholder='Enter discount code']");
   this.discount_submit = page.locator("//button[@value='Apply Discount']");
   this.discount_message = page.locator("//div[@data-role='checkout-messages']");
   this.discounttoggle = page.locator("//span[@id='block-discount-heading']");
   this.placeorder = page.locator("//button[@title='Place Order']");
   this.ordernumber = page.locator("//a[@class='order-number']//strong");
   this.cart_quantity = page.locator("//input[@data-role='cart-item-qty']");
   this.quiantity_update = page.locator("//button[@title='Update Cart']");
   this.cartedit = page.locator("//a[@class='action edit']");

    }


}

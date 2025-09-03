package ge.tbc.testautomation.steps;

import com.microsoft.playwright.ConsoleMessage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;
import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.pages.MagentoHomePage;
import ge.tbc.testautomation.pages.MainPage;
import org.testng.Assert;

import java.lang.invoke.ConstantCallSite;
import java.util.Collections;
import java.util.List;
import java.util.Spliterator;

public class MagentoHomePageSteps {
    Page page;
    MagentoHomePage magentohomepage;
    Constants constants;

    public MagentoHomePageSteps(Page page) {
        this.page = page;
        this.magentohomepage = new MagentoHomePage(page);
        this.constants = new Constants();
    }


    //reviewNumberTest:
    //Find an offer that has reviews and validate that the offer really
    // has as many reviews as it says.
    public void findRandomOffer() {
        List<Locator> offerItems = magentohomepage.offers.all();
        Collections.shuffle(offerItems);
        for (Locator l : offerItems) {
            if ((l.locator(magentohomepage.offers_review)).isVisible()) {
                l.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
                l.click();
                break;
            }
        }

    }


    public void resizeToMobile() {
        page.setViewportSize(245, 812);
    }

    public void openBurgerMenu() {
        Locator menuToggle = magentohomepage.menu_toggle;
        menuToggle.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        menuToggle.click();
    }

    //mobileNavigationTest:
    private void validateOption(String sectionText, String optionText) {
        Locator section = magentohomepage.menu_sections
                .filter(new Locator.FilterOptions().setHasText(sectionText))
                .first();

        section.click();
        Locator option;

        if (sectionText == "Account") {
            option = magentohomepage.acount_collapsibles
                    .filter(new Locator.FilterOptions().setHasText(optionText))
                    .first();
        } else {
            option = magentohomepage.menu_collapsibles
                    .filter(new Locator.FilterOptions().setHasText(optionText))
                    .first();
        }

        option.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        System.out.println(option.textContent());
        PlaywrightAssertions.assertThat(option).hasCount(1);
    }

    public void validateAccountLinks() {
        validateOption("Account", "Sign in");
        validateOption("Account", "Create an Account");
    }

    public void validateMainMenuLinks() {
        List<String> mainLinks = List.of("What's New", "Women", "Men", "Gear", "Training", "Sale");
        for (String link : mainLinks) {
            validateOption("Menu", link);
        }
    }



    public void searchFor(String keyword) {
        magentohomepage.search_input.fill(keyword);
        magentohomepage.search_button.click();
        Locator firstProduct = page.locator("ol.products li.product-item .product-item-link").first();
        firstProduct.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        String productName = firstProduct.textContent().trim();
        System.out.println("Opening product: " + productName);

        firstProduct.click();
    }

    public void addToCart() {
        magentohomepage.add_to_cart.click();

    }

    public void openMiniCart() {
        page.waitForTimeout(3000);

        magentohomepage.mini_cart.click();
    }


    public void validateProductName() {
        Locator cartItem = magentohomepage.cart_item_name.first();
        cartItem.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        String nameCart = cartItem.textContent().trim();
        String nameProduct = magentohomepage.product_name.textContent().trim();

        Assert.assertEquals(nameCart, nameProduct, "product names do not match");
    }


    public void purchaseItem() {
        //login();
        validateFavourites();
        openMiniCart();
        checkout();
        validateInfo();
        checkDiscount();
        validatesuccess();

    }

    //purhcase item
    public void login() {
        magentohomepage.sign_in.first().click();
        magentohomepage.email_input_signin.fill(constants.email);
        magentohomepage.pass_input_signin.first().fill(constants.password);
        magentohomepage.login_button.click();
    }

    public void validateFavourites() {

        page.navigate("https://magento.softwaretestingboard.com/wishlist/");
        List<Locator> items = magentohomepage.items_in_wishlsit.all();
        Assert.assertFalse(items.isEmpty());
        magentohomepage.add_all_to_cart.click();
    }

    public void checkout() {
        magentohomepage.checkout_button.first().click();
        magentohomepage.email.fill(constants.email);
        magentohomepage.first_name.fill(constants.firstname);
        magentohomepage.last_name.fill(constants.lastname);
        magentohomepage.street.fill(constants.street);
        magentohomepage.city.fill(constants.city);
        magentohomepage.region.selectOption(new SelectOption().setLabel(constants.region));
        //magentohomepage.country.selectOption(new SelectOption().setLabel(constants.country));
        magentohomepage.postcode.fill(constants.postcode);
        magentohomepage.telephone.fill(constants.telephone);
        magentohomepage.shipping.first().click();
        magentohomepage.submit.click();
    }

    public void validateInfo() {

        Locator shippingInfo = magentohomepage.shippinfo.first();

        shippingInfo.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE));

        String text = shippingInfo.innerText();


        Assert.assertTrue(text.contains(constants.firstname), "Firstname not found in shipping info");
        Assert.assertTrue(text.contains(constants.lastname), "Lastname not found in shipping info");
        Assert.assertTrue(text.contains(constants.street), "Street not found in shipping info");
        Assert.assertTrue(text.contains(constants.city), "City not found in shipping info");
        Assert.assertTrue(text.contains(constants.region), "Region not found in shipping info");
        Assert.assertTrue(text.contains(constants.postcode), "Postcode not found in shipping info");
        Assert.assertTrue(text.contains(constants.telephone), "Telephone not found in shipping info");
    }

    public void checkDiscount() {
        magentohomepage.discounttoggle.click();
        magentohomepage.discountinput.fill(constants.postcode);
        magentohomepage.discount_submit.click();
        // page.waitForTimeout(1000);


        Locator message = magentohomepage.discount_message.last();
        message.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.ATTACHED)
                .setTimeout(3000));

        Assert.assertFalse(message.isVisible(), "message dissapears");
        magentohomepage.placeorder.click();
    }

    public void validatesuccess() {
        String orderNumber = magentohomepage.ordernumber.textContent().trim();
        Assert.assertFalse(orderNumber.isEmpty(), "order number was not displayed");

        String pageTitle = page.title();
        Assert.assertTrue(pageTitle.contains("success"));
    }

    public void modifyquantity(){
        magentohomepage.cartedit.click();

        magentohomepage.quiantity_update.click();
        magentohomepage.cart_quantity.clear();
        magentohomepage.cart_quantity.fill("2");

    }


    public void add_random_product2Cart() {
        searchFor("Clamber Watch");
        addToCart();
        openMiniCart();
        modifyquantity();
        openMiniCart();
        checkout();
        validateInfo();



    }




}
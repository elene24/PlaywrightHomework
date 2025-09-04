package ge.tbc.testautomation.steps;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;
import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.pages.MagentoHomePage;
import io.qameta.allure.Step;
import org.testng.Assert;

import java.util.Collections;
import java.util.List;

public class MagentoHomePageSteps {
    Page page;
    MagentoHomePage magentohomepage;
    Constants constants;

    public MagentoHomePageSteps(Page page) {
        this.page = page;
        this.magentohomepage = new MagentoHomePage(page);
        this.constants = new Constants();
    }

    // reviewNumberTest
    @Step("Find a random offer with reviews and click it")
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

    @Step("Resize page to mobile viewport")
    public void resizeToMobile() {
        page.setViewportSize(245, 812);
    }

    @Step("Open burger menu")
    public void openBurgerMenu() {
        try {
            Locator menuToggle = magentohomepage.menu_toggle;
            menuToggle.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(60000));
            menuToggle.click();
        } catch (Exception e) {
            System.out.println("failed to open burger menu: " + e.getMessage());
        }
    }

    // mobileNavigationTest
    @Step("Validate menu option optionText in section sectionText")
    private void validateOption(String sectionText, String optionText) {
        Locator section = magentohomepage.menu_sections
                .filter(new Locator.FilterOptions().setHasText(sectionText))
                .first();
        section.click();
        Locator option;

        if (sectionText.equals("Account")) {
            option = magentohomepage.acount_collapsibles
                    .filter(new Locator.FilterOptions().setHasText(optionText))
                    .first();
        } else {
            option = magentohomepage.menu_collapsibles
                    .filter(new Locator.FilterOptions().setHasText(optionText))
                    .first();
        }

        option.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        PlaywrightAssertions.assertThat(option).hasCount(1);
    }

    @Step("Validate account links in burger menu")
    public void validateAccountLinks() {
        validateOption("Account", "Sign in");
        validateOption("Account", "Create an Account");
    }

    @Step("Validate main menu links")
    public void validateMainMenuLinks() {
        List<String> mainLinks = List.of("What's New", "Women", "Men", "Gear", "Training", "Sale");
        for (String link : mainLinks) {
            validateOption("Menu", link);
        }
    }

    @Step("Search for product with  keyword")
    public void searchFor(String keyword) {
        magentohomepage.search_input.fill(keyword);
        magentohomepage.search_button.click();
        Locator firstProduct = page.locator("ol.products li.product-item .product-item-link").first();
        firstProduct.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        firstProduct.click();
    }

    @Step("Add current product to cart")
    public void addToCart() {
        magentohomepage.add_to_cart.click();
    }

    @Step("Open mini cart")
    public void openMiniCart() {
        page.waitForTimeout(3000);
        magentohomepage.mini_cart.click();
    }

    @Step("Validate product name in cart matches product page")
    public void validateProductName() {
        Locator cartItem = magentohomepage.cart_item_name.first();
        cartItem.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        String nameCart = cartItem.textContent().trim();
        String nameProduct = magentohomepage.product_name.textContent().trim();
        Assert.assertEquals(nameCart, nameProduct, "product names do not match");
    }

    @Step("Purchase an item from wishlist")
    public void purchaseItem() {
        login();
        validateFavourites();
        openMiniCart();
        checkout();
        validateInfo();
        checkDiscount();
        validatesuccess();
    }

    @Step("Login with predefined credentials")
    public void login() {
        magentohomepage.sign_in.first().click();
        magentohomepage.email_input_signin.fill(constants.email);
        magentohomepage.pass_input_signin.first().fill(constants.password);
        magentohomepage.login_button.click();
    }

    @Step("Validate wishlist has items and add all to cart")
    public void validateFavourites() {
        page.navigate("https://magento.softwaretestingboard.com/wishlist/");
        List<Locator> items = magentohomepage.items_in_wishlsit.all();
        Assert.assertFalse(items.isEmpty());
        magentohomepage.add_all_to_cart.click();
    }

    @Step("Checkout process with shipping information")
    public void checkout() {
        magentohomepage.checkout_button.first().click();
        magentohomepage.email.fill(constants.email);
        magentohomepage.first_name.fill(constants.firstname);
        magentohomepage.last_name.fill(constants.lastname);
        magentohomepage.street.fill(constants.street);
        magentohomepage.city.fill(constants.city);
        magentohomepage.region.selectOption(new SelectOption().setLabel(constants.region));
        magentohomepage.postcode.fill(constants.postcode);
        magentohomepage.telephone.fill(constants.telephone);
        magentohomepage.shipping.first().click();
        magentohomepage.submit.click();
    }

    @Step("Validate shipping information")
    public void validateInfo() {
        Locator shippingInfo = magentohomepage.shippinfo.first();
        shippingInfo.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        String text = shippingInfo.innerText();
        Assert.assertTrue(text.contains(constants.firstname));
        Assert.assertTrue(text.contains(constants.lastname));
        Assert.assertTrue(text.contains(constants.street));
        Assert.assertTrue(text.contains(constants.city));
        Assert.assertTrue(text.contains(constants.region));
        Assert.assertTrue(text.contains(constants.postcode));
        Assert.assertTrue(text.contains(constants.telephone));
    }

    @Step("Apply discount code and place order")
    public void checkDiscount() {
        magentohomepage.discounttoggle.click();
        magentohomepage.discountinput.fill(constants.postcode);
        magentohomepage.discount_submit.click();
        Locator message = magentohomepage.discount_message.last();
        message.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.ATTACHED)
                .setTimeout(3000));
        Assert.assertFalse(message.isVisible());
        magentohomepage.placeorder.click();
    }

    @Step("Validate order success")
    public void validatesuccess() {
        String orderNumber = magentohomepage.ordernumber.textContent().trim();
        Assert.assertFalse(orderNumber.isEmpty(), "order number was not displayed");
        Assert.assertTrue(page.title().contains("success"));
    }

    @Step("Modify quantity in cart to 2")
    public void modifyquantity() {
        magentohomepage.cartedit.click();
        magentohomepage.quiantity_update.click();
        magentohomepage.cart_quantity.clear();
        magentohomepage.cart_quantity.fill("2");
    }

    @Step("Search for a product, add to cart, modify quantity, checkout and validate")
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

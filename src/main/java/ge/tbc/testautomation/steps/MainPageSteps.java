package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import ge.tbc.testautomation.pages.MainPage;
import org.testng.Assert;
import io.qameta.allure.Step;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainPageSteps {

    Page page;
    MainPage mainpage;

    public MainPageSteps(Page page) {
        this.page = page;
        this.mainpage = new MainPage(page);
    }

    @Step("Save a product to favorites while being unauthorized")
    public void saveToFavsUnauthorized() {
        openRandomProduct();
        clickAddToWishlist();
        validateUnauthorizedWishlistMessage();
        clickCreateAccount();
        registerNewUser("Elene", "Margalitadze", "elene667@example.com", "Margalitadze8663$");
        validateWelcomeMessage("Elene", "Margalitadze");
    }

    @Step("Check behavior when product is out of stock")
    public void outOfStock() {
        openFirstHotSellingItem();
        selectFirstSizeAndColor();
        clickAddToCart();
        validateOutOfStockMessage();
        openCart();
        validateCartEmpty();
    }

    @Step("Add a random product to cart")
    public void addRandomProductToCart() {
        searchFor("All");
        openRandomProduct();
        addToCart();
        openMiniCart();
        validatePrice();
        validateProductName();
    }

    @Step("Delete the first item from the cart")
    public void deleteFromCart() {
        openMiniCart();
        String name = getFirstCartItemName();
        removeFirstCartItem();
        verifyProductRemoved(name);
    }

    @Step("Pick 3 random hot-selling items")
    public List<Locator> pickRandomItems() {
        List<Locator> chosenItems = new ArrayList<>();
        List<Locator> allItems = mainpage.HOT_SELLING_ITEMS.all();
        Collections.shuffle(allItems, new Random());

        for (Locator item : allItems) {
            if (item.locator(mainpage.HOT_SELLING_ITEM_COLORS).isVisible()) {
                chosenItems.add(item.locator(mainpage.HOT_SELLING_ITEM_COLORS));
            }
            if (chosenItems.size() == 3) break;
        }
        return chosenItems;
    }

    @Step("Validate colors switch correctly for chosen items")
    public void validateColors(List<Locator> chosen) {
        for (Locator item : chosen) {
            item.locator(mainpage.HOT_SELLING_ITEM_COLORS).all().forEach(color -> {
                color.click();
                String colorLabel = color.getAttribute("aria-label");
                String imgSrc = item.locator("img[@class='product-image-photo']").getAttribute("src");
                Assert.assertTrue(imgSrc.contains(colorLabel), "Images don't switch correctly");
            });
        }
    }

    @Step("Open the first hot-selling item")
    public Locator openFirstHotSellingItem() {
        Locator firstItem = mainpage.HOT_SELLING_ITEMS.first();
        firstItem.scrollIntoViewIfNeeded();
        firstItem.hover();
        firstItem.click();
        return firstItem;
    }

    @Step("Select the first size and color available")
    public void selectFirstSizeAndColor() {
        List<Locator> sizes = mainpage.SIZE_SWATCH.all();
        List<Locator> colors = mainpage.COLOR_SWATCH.all();
        if (!sizes.isEmpty()) sizes.get(0).click();
        if (!colors.isEmpty()) colors.get(0).click();
    }

    @Step("Click Add to Cart")
    public void clickAddToCart() {
        Locator addButton = mainpage.ADD_TO_CART_BUTTON;
        addButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        addButton.click();
    }

    @Step("Validate Out of Stock message is displayed")
    public void validateOutOfStockMessage() {
        String message = mainpage.OUT_OF_STOCK_ERROR.textContent().trim();
        Assert.assertEquals(message, MainPage.OUT_OF_STOCK_MESSAGE);
    }

    @Step("Open the mini cart")
    public void openCart() {
        mainpage.MINI_CART_BUTTON.click();
    }

    @Step("Validate that cart is empty")
    public void validateCartEmpty() {
        String cartText = mainpage.CART_EMPTY_TEXT.textContent().trim();
        Assert.assertEquals(cartText, MainPage.CART_EMPTY_TEXT_VALUE);
    }

    @Step("Open a random product")
    public Locator openRandomProduct() {
        List<Locator> allItems = mainpage.HOT_SELLING_ITEMS.all();
        Collections.shuffle(allItems, new Random());
        Locator chosenProduct = allItems.get(0);
        chosenProduct.scrollIntoViewIfNeeded();
        chosenProduct.hover();
        chosenProduct.click();
        return chosenProduct;
    }

    @Step("Click Add to Wishlist")
    public void clickAddToWishlist() {
        mainpage.ADD_TO_WISHLIST.click();
    }

    @Step("Validate unauthorized wishlist message")
    public void validateUnauthorizedWishlistMessage() {
        Locator alertMessage = mainpage.UNAUTHORIZED_WISHLIST_MESSAGE;
        alertMessage.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(15000));
        String mess = alertMessage.textContent().trim();
        Assert.assertEquals(mess, MainPage.UNAUTHORIZED_WISHLIST_TEXT);
    }

    @Step("Click Create Account")
    public void clickCreateAccount() {
        Locator createAccount = mainpage.CREATE_ACCOUNT_BUTTON;
        createAccount.scrollIntoViewIfNeeded();
        createAccount.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(15000));
        createAccount.click();
    }

    @Step("Register a new user firstName lastName with email")
    public void registerNewUser(String firstName, String lastName, String email, String password) {
        mainpage.FIRST_NAME_INPUT.fill(firstName);
        mainpage.LAST_NAME_INPUT.fill(lastName);
        mainpage.EMAIL_INPUT.fill(email);
        mainpage.PASSWORD_INPUT.fill(password);
        mainpage.PASSWORD_CONFIRM_INPUT.fill(password);
        mainpage.CREATE_ACCOUNT_FORM_BUTTON.click();
    }

    @Step("Validate welcome message for firstName lastName")
    public void validateWelcomeMessage(String firstName, String lastName) {
        Locator welcomeMessage = mainpage.WELCOME_MESSAGE.first();
        welcomeMessage.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(15000));
        String message = welcomeMessage.textContent().trim();
        Assert.assertEquals(message, String.format(MainPage.WELCOME_TEXT_TEMPLATE, firstName, lastName));
    }

    @Step("Search for keyword keyword")
    public void searchFor(String keyword) {
        mainpage.SEARCH_INPUT.fill(keyword);
        mainpage.SEARCH_BUTTON.click();
    }

    @Step("Add product to cart")
    public void addToCart() {
        boolean added = false;
        Locator addBtn = mainpage.PRODUCT_ADD_TO_CART_BUTTON;
        List<Locator> sizes = mainpage.SIZE_SWATCH.all();
        List<Locator> colors = mainpage.COLOR_SWATCH.all();

        if (!sizes.isEmpty() && !colors.isEmpty()) {
            for (Locator size : sizes) {
                size.click();
                for (Locator color : colors) {
                    color.click();
                    addBtn.click();
                    page.waitForTimeout(2000);
                    if (!mainpage.OUT_OF_STOCK_ERROR.isVisible()) {
                        added = true;
                        break;
                    }
                }
                if (added) break;
            }
            if (!added) System.out.println("No available size/color combinations");
        } else {
            addBtn.click();
        }
    }

    @Step("Open mini cart")
    public void openMiniCart() {
        mainpage.MINI_CART_BUTTON.click();
    }

    @Step("Validate product price in cart matches product page")
    public void validatePrice() {
        String priceCart = mainpage.CART_PRICE.textContent().trim();
        String priceProduct = mainpage.PRODUCT_PRICE.textContent().trim();
        Assert.assertEquals(priceCart, priceProduct, "Prices do not match");
    }

    @Step("Validate product name in cart matches product page")
    public void validateProductName() {
        String nameCart = mainpage.CART_ITEM_NAME.textContent().trim();
        String nameProduct = mainpage.PRODUCT_NAME.textContent().trim();
        Assert.assertEquals(nameCart, nameProduct, "Product names do not match");
    }

    @Step("Get name of first item in cart")
    public String getFirstCartItemName() {
        Locator firstItemName = mainpage.CART_ITEM_NAME.first();
        return firstItemName.textContent().trim();
    }

    @Step("Remove first item from cart")
    public void removeFirstCartItem() {
        Locator removeBtn = mainpage.CART_ITEM_REMOVE_BUTTON.first();
        removeBtn.click();
        mainpage.CONFIRM_REMOVE_BUTTON.click();
    }

    @Step("Verify that product productName is removed from cart")
    public void verifyProductRemoved(String productName) {
        Locator cartItems = mainpage.CART_ITEM_NAME;
        Locator matchingItems = cartItems.filter(new Locator.FilterOptions().setHasText(productName));
        Assert.assertTrue(matchingItems.count() == 0, "Product was not removed from cart");
    }
}

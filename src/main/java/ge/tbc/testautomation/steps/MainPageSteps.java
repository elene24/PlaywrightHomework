package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import ge.tbc.testautomation.pages.MainPage;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainPageSteps {

    Page page;
    MainPage mainpage;

    public MainPageSteps(Page page) {
        this.page = page;
        this.mainpage = new MainPage();
    }




    // Final adding to favourites while being unauthoried
    public void saveToFavsUnauthorized() {
        openRandomProduct();
        clickAddToWishlist();
        validateUnauthorizedWishlistMessage();
        clickCreateAccount();
        registerNewUser("Elene", "Margalitadze", "elene765@example.com", "Margalitadze8663$");
        validateWelcomeMessage("Elene", "Margalitadze");
    }


    // final out of stock test
    public void outOfStock() {
        openFirstHotSellingItem();
        selectFirstSizeAndColor();
        clickAddToCart();
        validateOutOfStockMessage();
        openCart();
        validateCartEmpty();
    }
//final adding random products to cart
    public void addRandomProductToCart() {
        searchFor("All");
        openRandomProduct();
        addToCart();
        openMiniCart();
        validatePrice();
        validateProductName();
    }

//final deleting an item from cart
    public void deleteFromCart() {
        openMiniCart();
        String name = getFirstCartItemName();
        removeFirstCartItem();
        verifyProductRemoved(name);
    }


    public List<Locator> pickRandomItems() {
        List<Locator> chosenItems = new ArrayList<>();
        List<Locator> allItems = page.locator(MainPage.HOT_SELLING_ITEMS).all();
        Collections.shuffle(allItems, new Random());

        for (Locator item : allItems) {
            if (item.locator(MainPage.HOT_SELLING_ITEM_COLORS).isVisible()) {
                chosenItems.add(item.locator(MainPage.HOT_SELLING_ITEM_COLORS));
            }
            if (chosenItems.size() == 3) break;
        }

        return chosenItems;
    }

    public void validateColors(List<Locator> chosen) {
        for (Locator item : chosen) {
            item.locator(MainPage.HOT_SELLING_ITEM_COLORS).all().forEach(color -> {
                color.click();
                String colorLabel = color.getAttribute("aria-label");
                String imgSrc = item.locator("img[@class='product-image-photo']").getAttribute("src");
                Assert.assertTrue(imgSrc.contains(colorLabel), "images don't switch correctly");
            });
        }
    }

    public Locator openFirstHotSellingItem() {
        Locator firstItem = page.locator(MainPage.HOT_SELLING_ITEMS).first();
        firstItem.scrollIntoViewIfNeeded();
        firstItem.hover();
        firstItem.click();
        return firstItem;
    }

    public void selectFirstSizeAndColor() {
        List<Locator> sizes = page.locator(MainPage.SIZE_SWATCH).all();
        List<Locator> colors = page.locator(MainPage.COLOR_SWATCH).all();
        if (!sizes.isEmpty()) sizes.get(0).click();
        if (!colors.isEmpty()) colors.get(0).click();
    }

    public void clickAddToCart() {
        Locator addButton = page.locator(MainPage.ADD_TO_CART_BUTTON);
        addButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        addButton.click();
    }

    public void validateOutOfStockMessage() {
        String message = page.locator(MainPage.OUT_OF_STOCK_ERROR).textContent().trim();
        Assert.assertEquals(message, MainPage.OUT_OF_STOCK_MESSAGE);
    }

    public void openCart() {
        page.locator(MainPage.MINI_CART_BUTTON).click();
    }

    public void validateCartEmpty() {
        String cartText = page.locator(MainPage.CART_EMPTY_TEXT).textContent().trim();
        Assert.assertEquals(cartText, MainPage.CART_EMPTY_TEXT_VALUE);
    }




    public Locator openRandomProduct() {
        List<Locator> allItems = page.locator(MainPage.HOT_SELLING_ITEMS).all();
        Collections.shuffle(allItems, new Random());
        Locator chosenProduct = allItems.get(0);
        chosenProduct.scrollIntoViewIfNeeded();
        chosenProduct.hover();
        chosenProduct.click();
        return chosenProduct;
    }

    public void clickAddToWishlist() {
        page.waitForSelector(MainPage.ADD_TO_WISHLIST).click();
    }

    public void validateUnauthorizedWishlistMessage() {
        Locator alertMessage = page.locator(MainPage.UNAUTHORIZED_WISHLIST_MESSAGE);
        alertMessage.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(15000));
        String mess = alertMessage.textContent().trim();
        Assert.assertEquals(mess, MainPage.UNAUTHORIZED_WISHLIST_TEXT);
    }

    public void clickCreateAccount() {
        Locator createAccount = page.locator(MainPage.CREATE_ACCOUNT_BUTTON);
        createAccount.scrollIntoViewIfNeeded();
        createAccount.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(15000));
        createAccount.click();
    }

    public void registerNewUser(String firstName, String lastName, String email, String password) {
        page.locator(MainPage.FIRST_NAME_INPUT).fill(firstName);
        page.locator(MainPage.LAST_NAME_INPUT).fill(lastName);
        page.locator(MainPage.EMAIL_INPUT).fill(email);
        page.locator(MainPage.PASSWORD_INPUT).fill(password);
        page.locator(MainPage.PASSWORD_CONFIRM_INPUT).fill(password);
        page.locator(MainPage.CREATE_ACCOUNT_FORM_BUTTON).click();
    }

    public void validateWelcomeMessage(String firstName, String lastName) {
        Locator welcomeMessage = page.locator(MainPage.WELCOME_MESSAGE).first();
        welcomeMessage.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(15000));
        String message = welcomeMessage.textContent().trim();
        Assert.assertEquals(message, String.format(MainPage.WELCOME_TEXT_TEMPLATE, firstName, lastName));
    }



    public void searchFor(String keyword) {
        page.locator(MainPage.SEARCH_INPUT).fill(keyword);
        page.locator(MainPage.SEARCH_BUTTON).click();
    }

    public void addToCart() {
        boolean added = false;
        Locator addBtn = page.locator(MainPage.PRODUCT_ADD_TO_CART_BUTTON);
        List<Locator> sizes = page.locator(MainPage.SIZE_SWATCH).all();
        List<Locator> colors = page.locator(MainPage.COLOR_SWATCH).all();

        if (!sizes.isEmpty() && !colors.isEmpty()) {
            for (Locator size : sizes) {
                size.click();
                for (Locator color : colors) {
                    color.click();
                    addBtn.click();
                    page.waitForTimeout(2000);
                    if (!page.locator(MainPage.OUT_OF_STOCK_ERROR).isVisible()) {
                        added = true;
                        break;
                    }
                }
                if (added) break;
            }
            if (!added) System.out.println("no available size/color combinations");
        } else {
            addBtn.click();
        }
    }

    public void openMiniCart() {
        page.locator(MainPage.MINI_CART_BUTTON).click();
    }

    public void validatePrice() {
        String priceCart = page.locator(MainPage.CART_PRICE).textContent().trim();
        String priceProduct = page.locator(MainPage.PRODUCT_PRICE).textContent().trim();
        Assert.assertEquals(priceCart, priceProduct, "prices do not match");
    }

    public void validateProductName() {
        String nameCart = page.locator(MainPage.CART_ITEM_NAME).textContent().trim();
        String nameProduct = page.locator(MainPage.PRODUCT_NAME).textContent().trim();
        Assert.assertEquals(nameCart, nameProduct, "product names do not match");
    }



    public String getFirstCartItemName() {
        Locator firstItemName = page.locator(MainPage.CART_ITEM_NAME).first();
        return firstItemName.textContent().trim();
    }

    public void removeFirstCartItem() {
        Locator removeBtn = page.locator(MainPage.CART_ITEM_REMOVE_BUTTON).first();
        removeBtn.click();
        page.waitForSelector(MainPage.CONFIRM_REMOVE_BUTTON).click();
    }

    public void verifyProductRemoved(String productName) {
        Locator cartItems = page.locator(MainPage.CART_ITEM_NAME);
        Locator matchingItems = cartItems.filter(new Locator.FilterOptions().setHasText(productName));
        boolean isRemoved = matchingItems.count() == 0;
        Assert.assertTrue(isRemoved, "the product was not removed from the cart");
    }

}

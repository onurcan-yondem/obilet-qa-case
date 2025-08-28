package pages.hotel;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class HotelsPage extends BasePage {
    @FindBy(id = "boardItems_41")
    private WebElement halfBoardCheckbox;

    @FindBy(xpath = "*//span[text()='Yarım Pansiyon ']/span")
    private WebElement halfBoardFilterCount;

    @FindBy(id = "filterSortingDropdown")
    private WebElement filterSortingDropdown;

    @FindBy(xpath = "*//button[@data-value='price-lowest']")
    private WebElement priceLowestFilterButton;

    @FindBy(css = "div.hotel-price__amount")
    private List<WebElement> hotelPriceTexts;

    @FindBy(css = "#searchResultCountContainer > span")
    private WebElement resultCountText;

    @FindBy(id = "hotelSearchContainer")
    private WebElement hotelSearchContainer;

    @FindBy(xpath = "*//div[@class='hotel-offer__name' and text()='Yarım Pansiyon']")
    private WebElement halfBoardText;

    @FindBy(className = "hotel-coupon-modal__close-btn")
    private WebElement couponModalCloseButton;

    @FindBy(className = "skeleton-loading")
    private WebElement loadingElement;

    @Override
    public boolean isAt() {
        return hotelSearchContainer.isDisplayed()
                && Objects.requireNonNull(driver.getTitle()).contains("Otel Fiyatları! - obilet.com");
    }

    @Step("Half board results are shown")
    public void filterHalfBoard() {
        click(halfBoardCheckbox);
        takeScreenshot();
    }

    @Step("Half board results are checked")
    public boolean checkHalfBoard() {
        boolean isHalfBoard = true;
        int loadedHotelCount = hotelPriceTexts.size();
        List<WebElement> checkHotels = new ArrayList<>();

        checkHotels.add(hotelPriceTexts.get(1));
        checkHotels.add(hotelPriceTexts.get(loadedHotelCount / 2));
        checkHotels.add(hotelPriceTexts.get(loadedHotelCount - 1));

        for (WebElement hotel : checkHotels) {
            jsExecutor.executeScript("arguments[0].click();", hotel);
            switchToNewWindow();
            click(couponModalCloseButton);
            isHalfBoard = isHalfBoard && halfBoardText.isDisplayed();
            takeScreenshot();
            switchToMainWindow();
        }

        return isHalfBoard;
    }

    @Step("Hotel prices are sorted ascending")
    public void sortAscendingHotels() {
        click(filterSortingDropdown);
        click(priceLowestFilterButton);
        takeScreenshot();
    }

    @Step("Hotels are checked")
    public HashMap<String, String> getHotelCount() {
        HashMap<String, String> counts = new HashMap<>();
        String filterCount = getText(halfBoardFilterCount).replaceAll("[^0-9]", "");
        String resultCount = getText(resultCountText).replaceAll("[^0-9]", "");
        String hotelCount = Integer.toString(hotelPriceTexts.size());

        counts.put("filterCount", filterCount);
        counts.put("resultCount", resultCount);
        counts.put("hotelCount", hotelCount);

        return counts;
    }

    @Step("Hotel prices are checked")
    public List<Integer> getHotelPrices() {
        List<Integer> hotelPriceList = new ArrayList<>();

        for (WebElement hotelPriceElement : hotelPriceTexts) {
            String priceText = getText(hotelPriceElement).replaceAll("[^0-9]", "");
            hotelPriceList.add(Integer.parseInt(priceText));
        }

        takeScreenshot();
        return hotelPriceList;
    }
}

package pages.hotel;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;

import java.util.List;
import java.util.Objects;

public class HotelHomePage extends BasePage {
    @FindBy(xpath = "*//a[@data-event-action='Hotel']")
    private WebElement hotelButton;

    @FindBy(id = "origin-input")
    private WebElement originInput;

    @FindBy(id = "search-button")
    private WebElement searchHotelButton;

    @FindBy(css = "div.origin-name")
    private List<WebElement> originDropdownItems;

    @Override
    public boolean isAt() {
        return searchHotelButton.isDisplayed()
                && Objects.equals(driver.getTitle(), "Ucuz Otobüs Bileti Fiyatları, Otobüs Bileti Al - obilet.com");
    }

    @Step("Search for a hotel in {0} location")
    public void searchHotel(String origin) {
        click(hotelButton);
        sendKeys(originInput, origin);

        for (WebElement originDropdownItem : originDropdownItems) {
            if (getText(originDropdownItem).equals(origin)) {
                click(originDropdownItem);
                break;
            }
        }
        takeScreenshot();
        click(searchHotelButton);
    }
}

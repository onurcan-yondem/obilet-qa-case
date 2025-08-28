package pages.bus;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;

import java.util.Objects;

public class BusHomePage extends BasePage {
    @FindBy(css = "a[data-event-action='Bus']")
    private WebElement busButton;

    @FindBy(id = "origin-input")
    private WebElement originInput;

    @FindBy(id = "destination-input")
    private WebElement destinationInput;

    @FindBy(id = "search-button")
    private WebElement searchBusButton;

    @FindBy(id = "tomorrow")
    private WebElement tomorrowRadioButton;

    @FindBy(className = "skeleton-loading")
    private WebElement loadingElement;

    @Override
    public boolean isAt() {
        return originInput.isDisplayed()
                && destinationInput.isDisplayed()
                && Objects.equals(driver.getTitle(), "Ucuz Otobüs Bileti Fiyatları, Otobüs Bileti Al - obilet.com");
    }

    @Step("Search from {0} to {1} bus trips")
    public void searchTrips(String origin, String destination) {
        sendKeys(originInput, origin);
        sendKeys(destinationInput, destination);
        click(tomorrowRadioButton);
        takeScreenshot();
        click(searchBusButton);
    }
}

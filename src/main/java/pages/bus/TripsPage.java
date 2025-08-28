package pages.bus;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class TripsPage extends BasePage {
    @FindBy(xpath = "*//li[contains(@id, 'journey')]")
    private List<WebElement> journeyListItems;

    @FindBy(css = "span.origin.location")
    private List<WebElement> departureBusTerminalText;

    @FindBy(css = "span.destination.location")
    private List<WebElement> arrivalBusTerminalText;

    @FindBy(className = "departure")
    private List<WebElement> departureTimeText;

    @FindBy(className = "available")
    private List<WebElement> busSeats;

    @FindBy(css = "button.male")
    private WebElement passengerMale;

    @FindBy(css = "button.female")
    private WebElement passengerFemale;

    @FindBy(xpath = "*//div[@class='seats']//img")
    private List<WebElement> selectedSeats;

    @FindBy(xpath = "*//div[@class='price ']//span[@class='amount']")
    private WebElement selectedSeatsPrice;

    @FindBy(className = "ready")
    private WebElement confirmSeatButton;

    @Override
    public boolean isAt() {
        return Objects.requireNonNull(driver.getTitle()).contains("Otobüs Seferi - obilet.com");
    }

    @Step("First journey selected")
    public HashMap<String, String> selectJourney() {
        HashMap<String, String> journeyInformation = new HashMap<>();

        for(WebElement journey : journeyListItems) {
            boolean isFull = Objects.requireNonNull(journey.getAttribute("class")).contains("full");
            if (!isFull) {
                click(journey);
                break;
            }
        }

        String departureBusTerminal = getText(departureBusTerminalText.get(0));
        String arrivalBusTerminal = getText(arrivalBusTerminalText.get(0));
        String departureTime = getText(departureTimeText.get(0));

        journeyInformation.put("departureBusTerminal", departureBusTerminal);
        journeyInformation.put("arrivalBusTerminal", arrivalBusTerminal);
        journeyInformation.put("departureTime", departureTime);

        return journeyInformation;
    }

    @Step("A seat number is selected")
    public HashMap<String, String> selectSeat() {
        HashMap<String, String> passengerInformation = new HashMap<>();
        List<WebElement> availableSeats = busSeats;

        for (WebElement availableSeat : availableSeats) {
            String availableType = availableSeat.getAttribute("obilet:available");
            click(availableSeat);
            switch (Objects.requireNonNull(availableType)) {
                case "F":
                    click(passengerFemale);
                    break;
                case "M", "A", "all":
                    click(passengerMale);
                    break;
                default:
                    throw new RuntimeException("Wrong available seat type!");
            }
            String seatNumber = availableSeat.getAttribute("obilet:seat");
            String totalSeatsPrice = getText(selectedSeatsPrice);
            takeScreenshot();

            passengerInformation.put("seatNumber", seatNumber);
            passengerInformation.put("totalSeatsPrice", totalSeatsPrice);
            break;
        }
        return passengerInformation;
    }

    @Step("Seat selection confirmed")
    public void redirectPayStep() {
        takeScreenshot();
        click(confirmSeatButton);
    }
}

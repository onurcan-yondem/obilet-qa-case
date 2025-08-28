package pages.bus;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;

import java.util.HashMap;
import java.util.Objects;

public class PaymentPage extends BasePage {
    @FindBy(className = "partner-name")
    private WebElement partnerNameText;

    @FindBy(xpath = "(*//div[@class='intro-table']//td)[1]")
    private WebElement fromTableData;

    @FindBy(xpath = "(*//div[@class='intro-table']//td)[2]")
    private WebElement toTableData;

    @FindBy(xpath = "(*//div[@class='intro-table']//td)[3]")
    private WebElement departureTableData;

    @FindBy(xpath = "(*//div[@class='intro-table']//td)[4]")
    private WebElement busSeatNumberTableData;

    @FindBy(id = "amount")
    private WebElement totalAmountText;

    @FindBy(className = "passengers-contact-info-content")
    private WebElement contactInfoContent;

    @FindBy(id = "payment")
    private WebElement paymentContent;

    @Override
    public boolean isAt() {
        return paymentContent.isDisplayed()
                && contactInfoContent.isDisplayed()
                && Objects.equals(driver.getTitle(), "Satın Al - obilet.com");
    }

    @Step("Get payment summary")
    public HashMap<String, String> getTripSummary() {
        HashMap<String, String> summary = new HashMap<>();
        takeScreenshot();

        String partnerName = getText(partnerNameText);
        String origin = getText(fromTableData);
        String destination = getText(toTableData);
        String departure = getText(departureTableData);
        String seatNumber = getText(busSeatNumberTableData);
        String totalAmount = getText(totalAmountText);

        summary.put("partnerName", partnerName);
        summary.put("origin", origin);
        summary.put("destination", destination);
        summary.put("departure", departure);
        summary.put("seatNumber", seatNumber);
        summary.put("totalAmount", totalAmount);

        return summary;
    }
}

package pages;

import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public abstract class BasePage {
    protected static final int DEFAULT_TIMEOUT = 30;
    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final JavascriptExecutor jsExecutor;

    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        this.jsExecutor = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isAt(String expectedTitle) {
        return wait.until(ExpectedConditions.titleContains(expectedTitle));
    }

    public abstract boolean isAt();

    protected void click(WebElement element) {
        int attempts = 0;

        while (attempts < 3) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(element)).click();
                return;
            } catch (StaleElementReferenceException ignored) {}
            attempts++;
            try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        }

        throw new RuntimeException("Element is not clickable!");
    }

    protected void sendKeys(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element)).sendKeys(text);
    }

    protected String getText(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element)).getText();
    }

    protected Set<String> getAllWindowHandles() {
        return driver.getWindowHandles();
    }

    protected void switchToNewWindow() {
        String currentWindow = driver.getWindowHandle();
        for (String handle : getAllWindowHandles()) {
            if (!handle.equals(currentWindow)) {
                driver.switchTo().window(handle);
                break;
            }
        }
    }

    protected void switchToMainWindow() {
        String mainWindow = driver.getWindowHandles().iterator().next();
        driver.close();
        driver.switchTo().window(mainWindow);
    }

    protected void takeScreenshot() {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            File graphics = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
            BufferedImage image = ImageIO.read(graphics);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            byte[] imageData = baos.toByteArray();
            Allure.addAttachment(dtf.format(now), new ByteArrayInputStream(imageData));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void waitForLoadingToDisappear(WebElement element) {
        try {
            wait.until(ExpectedConditions.invisibilityOfAllElements(element));
        } catch (Exception e) {
            System.out.println("Loading element not found or already disappeared!");
        }
    }

}

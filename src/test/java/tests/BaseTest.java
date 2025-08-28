package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.DriverManager;

import java.lang.reflect.Method;

public abstract class BaseTest {
    protected static final String BASE_URL = "https://www.obilet.com";

    @BeforeMethod
    public void setUp(Method method) {
        try {
            DriverManager.createDriver("chrome", false);
            WebDriver driver = DriverManager.getDriver();

            driver.get(BASE_URL);

        } catch (Exception e) {
            throw new RuntimeException("Setup failed: " + e.getMessage(), e);
        }
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }
}

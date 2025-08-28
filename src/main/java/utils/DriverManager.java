package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class DriverManager {
    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static WebDriver createDriver(String browserName, boolean headless) {
        WebDriver driver = null;

        try {
            switch (browserName.toLowerCase()) {
                case "chrome":
                    driver = createChromeDriver(headless);
                    break;
                case "firefox":
                    //firefox yapılandırmaları
                    break;
                case "edge":
                    //edge yapılandırmaları
                    break;
                default:
                    driver = createChromeDriver(headless);
            }

            if (driver == null) {
                throw new RuntimeException("Webdriver cannot be null!");
            }

            configureDriver(driver);

            setDriver(driver);
        } catch (Exception e) {
            throw new RuntimeException("WebDriver not created!");
        }

        return driver;
    }

    public static WebDriver createChromeDriver(boolean headless) {
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--disable-extensions");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-web-security");
        options.addArguments("--disable-features=VizDisplayCompositor");

        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        }

        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");

        options.setExperimentalOption("useAutomationExtension", false);
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

        return new ChromeDriver(options);
    }

    private static void configureDriver(WebDriver driver) {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().deleteAllCookies();
    }

    public static void setDriver(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("Webdriver is not null!");
        }
        driverThreadLocal.set(driver);
    }

    public static WebDriver getDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver == null) {
            throw new IllegalStateException("WebDriver is not initialized!");
        }
        return driver;
    }

    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                throw new RuntimeException("An error occurred while closing the driver");
            } finally {
                removeDriver();
            }
        }
    }

    public static void removeDriver() {
        driverThreadLocal.remove();
    }
}

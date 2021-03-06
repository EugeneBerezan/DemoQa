package utils;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class Driver {

    private static final int TIME_TO_WAIT_IN_SECONDS = 10;
    private static WebDriver driver = null;
    private static String nodeURL = "http://localhost:4444/wd/hub";
    private static String browser = System.getProperty("browserName");

    private Driver() {

    }

    public static WebDriver getInstance() throws MalformedURLException {
        if (driver == null) {
            getDriver(browser);
//            getHub(browser);
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(TIME_TO_WAIT_IN_SECONDS, TimeUnit.SECONDS);
        }
        return driver;
    }

    private static WebDriver getHub(String browser) throws MalformedURLException {
        switch (browser) {
            case "Chrome":
                System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
                DesiredCapabilities capabilitiesChrome = DesiredCapabilities.chrome();
                capabilitiesChrome.setBrowserName(browser.toLowerCase());
                capabilitiesChrome.setPlatform(Platform.LINUX);
//                capabilitiesChrome.setPlatform(Platform.MAC);
                capabilitiesChrome.setVersion("");
                driver = new RemoteWebDriver(new URL(nodeURL), capabilitiesChrome);
                break;
            case "Firefox":
                System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver");
                DesiredCapabilities capabilitiesFirefox = DesiredCapabilities.firefox();
                capabilitiesFirefox.setBrowserName(browser.toLowerCase());
                capabilitiesFirefox.setPlatform(Platform.LINUX);
//                capabilitiesFirefox.setPlatform(Platform.MAC);
                driver = new RemoteWebDriver(new URL(nodeURL), capabilitiesFirefox);
                break;
            default:
                throw new NoSuchElementException("Invalid browser name");
        }
        return driver;
    }

    private static WebDriver getDriver(String browser) {
        switch (browser) {
            case "Chrome":
                System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
                driver = new ChromeDriver();
                break;
            case "Firefox":
                System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver");
                driver = new FirefoxDriver();
                break;
            default:
                throw new NoSuchElementException("Invalid browser name");
        }
        return driver;
    }

    public static void close() throws MalformedURLException {
        Driver.getInstance().quit();
        driver = null;
    }

}

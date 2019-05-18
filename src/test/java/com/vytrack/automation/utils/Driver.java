package com.vytrack.automation.utils;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Driver {
    private static final Logger LOGGER = LogManager.getLogger(Driver.class);
    private static WebDriver driver;
    private static String browser;

    public static void setDriver(WebDriver lunchdriver) {
        driver = lunchdriver;

    }

    public static WebDriver getDriver() {
        if(driver == null)
            initializeDriver();
        return driver;
    }

    private Driver() {

    }

    public static WebDriver initializeDriver() {
        if (driver == null) {
            System.out.println("Browser: " + System.getProperty("BROWSER"));
            if (System.getProperty("BROWSER") == null) {
                browser = ConfigurationReader.getProperty("browser");
            } else {
                browser = System.getProperty("BROWSER");
            }
            switch (browser) {
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;
                case "chrome":
                    System.out.println("----Chrome----");
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--disable-popup-blocking");
                    options.addArguments("start-maximized");
                    options.addArguments("test-type");
                    options.addArguments("allow-running-insecure-content");
                    options.addArguments("disable-extensions");
                    options.addArguments("--ignore-certificate-errors");
                    options.addArguments("test-type=browser");
                    options.addArguments("disable-infobars");
                    driver = new ChromeDriver(options);
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                    break;
                case "ie":
                    WebDriverManager.iedriver().setup();
                    driver = new InternetExplorerDriver();
                    break;
                case "remotechrome":
                    DesiredCapabilities capabilities = new DesiredCapabilities().chrome();
                    capabilities.setPlatform(Platform.ANY);
                    try {
                        driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
                    } catch (MalformedURLException e) {
                        LOGGER.error(e.getMessage());
                        throw new RuntimeException(e.getMessage());
                    }
                    break;
                case "remotefirefox":
                    DesiredCapabilities firefoxcapabilities = new DesiredCapabilities().firefox();
                    firefoxcapabilities.setPlatform(Platform.ANY);
                    try {
                        driver = new RemoteWebDriver(new URL(ConfigurationReader.getProperty("huburl")),
                                firefoxcapabilities);
                    } catch (MalformedURLException e) {
                        LOGGER.error(e.getMessage());
                        throw new RuntimeException(e.getMessage());
                    }
                    break;
            }
            LOGGER.error(String.format("Initializing WebDriver with browser %s ", browser));
            setDriver(driver);
        }

        return driver;
    }

    public static void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}

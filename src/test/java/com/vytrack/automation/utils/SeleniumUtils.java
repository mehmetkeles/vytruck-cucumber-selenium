package com.vytrack.automation.utils;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;


public class SeleniumUtils {
    private static final Logger LOGGER = LogManager.getLogger(SeleniumUtils.class);

    public static void switchToWindowByTitle(String title) {
        Set<String> windows = Driver.getDriver().getWindowHandles();
        System.out.println("Amount of windows that are currently present :: " + windows.size());
        for (String window : windows) {
            Driver.getDriver().switchTo().window(window);
            if (Driver.getDriver().getTitle().startsWith(title)
                    || Driver.getDriver().getTitle().equalsIgnoreCase(title)) {
                break;
            } else {
                continue;
            }
        }
    }

    public static void clickWithJS(WebElement elementtoclick) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(),
                Long.parseLong(ConfigurationReader.getProperty("timeout")));
        wait.until(ExpectedConditions.elementToBeClickable(elementtoclick));
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", elementtoclick);
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", elementtoclick);
    }

    public static void waitForPresenceOfElementByCss(String css) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(),
                Long.parseLong(ConfigurationReader.getProperty("timeout")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(css)));
    }

    public static void waitForVissibilityOfElement(WebElement element) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(),
                Long.parseLong(ConfigurationReader.getProperty("timeout")));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForElement(WebElement element) {
        int i = 0;
        while (i < 10) {
            try {
                element.isDisplayed();
                break;
            } catch (WebDriverException e) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e1) {
                    LOGGER.error(e1.getMessage());
                }
                e.printStackTrace();
                i++;
            }
        }
    }

    public static boolean verifyElementIsNotPresent(String xpath) {
        List<WebElement> elemetns = Driver.getDriver().findElements(By.xpath(xpath));
        return elemetns.size() == 0;
    }

    public static boolean verifyElementIsNotPresent(By by) {
        List<WebElement> elemetns = Driver.getDriver().findElements(by);
        return elemetns.size() == 0;
    }

    public static void scrollToElement(WebElement element) {
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void hitEnterUsingRobot() {
        Robot rb;
        try {
            rb = new Robot();
            rb.keyPress(KeyEvent.VK_ENTER);
            rb.keyRelease(KeyEvent.VK_ENTER);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("Unable to press enter key.");
        }
    }

    public static boolean verifyAlertPresent() {
        try {
            Driver.getDriver().switchTo().alert();
            return true;
        } catch (NoAlertPresentException Ex) {
            LOGGER.error("Alert is not presenet");
        }
        return false;
    }

    public static void takeSnapShot() {
        try {
            TakesScreenshot scrShot = ((TakesScreenshot) Driver.getDriver());
            File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
            String path = System.getProperty("user.dir") + "\\screenshots.jpg";
            System.out.println(path);
            File DestFile = new File(path);
            FileUtils.copyFile(SrcFile, DestFile);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            LOGGER.error("Unable to take a snapshot");
        }

    }

    public static void waitUntilPageLoad() {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(),
                Integer.valueOf(ConfigurationReader.getProperty("timeout")));
        wait.until((d) -> {
            Boolean isPageLoaded = (Boolean) ((JavascriptExecutor) Driver.getDriver())
                    .executeScript("return document.readyState").equals("complete");
            if (!isPageLoaded)
                System.out.println("Document is loading");
            return isPageLoaded;
        });
    }

    public static void waitForStaleElement(WebElement element) {
        int y = 0;
        while (y <= 15) {
            try {
                element.isDisplayed();
                break;
            } catch (StaleElementReferenceException st) {
                y++;
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (WebDriverException we) {
                y++;
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static WebElement waitForVisibility(WebElement element, int timeToWaitInSec) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeToWaitInSec);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForVisibility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeout);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickablility(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeout);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement waitForClickablility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeout);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void waitForPageToLoad(long timeOutInSeconds) {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
        try {
            System.out.println("Waiting for page to load...");
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeOutInSeconds);
            wait.until(expectation);
        } catch (Throwable error) {
            System.out.println(
                    "Timeout waiting for Page Load Request to complete after " + timeOutInSeconds + " seconds");
        }
    }

    public static String convertDateFormat(String OriginalFormat, String TargetFormat, String Date) {
        DateFormat original = new SimpleDateFormat(OriginalFormat, Locale.ENGLISH);
        DateFormat target = new SimpleDateFormat(TargetFormat);
        String formattedDate = null;
        try {
            Date date = original.parse(Date);
            formattedDate = target.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    public static int getRandomNumber(int max) {
        return max == 0 ? 0 : new Random().nextInt(max);
    }

    public static WebElement selectRandomElement(List<WebElement> elements) {
        return elements != null ? elements.get(getRandomNumber(elements.size())) : null;
    }

    public static void createFileWithContent(String filePath, String content) {
        File file = new File(filePath);

        try {
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            try {
                fw.write(content);
            } catch (Exception e) {
                LOGGER.debug("Error during FileWriter append. " + e.getMessage(), e.getCause());
            } finally {
                try {
                    fw.close();
                } catch (Exception e) {
                    LOGGER.debug("Error during FileWriter close. " + e.getMessage(), e.getCause());
                }
            }

        } catch (IOException e) {
            LOGGER.debug(e.getMessage(), e.getCause());
        }
    }
}

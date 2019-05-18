package com.vytrack.automation.pages;

import com.vytrack.automation.utils.ConfigurationReader;
import com.vytrack.automation.utils.Driver;
import com.vytrack.automation.utils.SeleniumUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    private static final Logger LOGGER = LogManager.getLogger(LoginPage.class);

    public LoginPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(id = "prependedInput")
    private WebElement usernameelement;

    @FindBy(id = "prependedInput2")
    private WebElement passwordelement;

    @FindBy(id = "_submit")
    private WebElement loginbuttonelement;

    public void login(String userName, String password) {
        SeleniumUtils.waitForVissibilityOfElement(usernameelement);
        usernameelement.clear();
        usernameelement.sendKeys(userName);
        passwordelement.clear();
        passwordelement.sendKeys(password);
        SeleniumUtils.waitForClickablility(loginbuttonelement, 10);
        SeleniumUtils.clickWithJS(loginbuttonelement);

    }

    public void login() {
        String username = ConfigurationReader.getProperty("username" + ConfigurationReader.getProperty("environment"));
        String password = ConfigurationReader.getProperty("password" + ConfigurationReader.getProperty("environment"));
        LOGGER.info("User name: "+username);
        LOGGER.info("User password: "+password);
        SeleniumUtils.waitForVissibilityOfElement(usernameelement);
        usernameelement.clear();
        usernameelement.sendKeys(username);
        passwordelement.clear();
        passwordelement.sendKeys(password);
        SeleniumUtils.waitForClickablility(loginbuttonelement, 10);
        SeleniumUtils.clickWithJS(loginbuttonelement);

    }

    public void goToHomePage() {
        String url = ConfigurationReader.getProperty("url" + ConfigurationReader.getProperty("environment"));
        try {
            Driver.getDriver().navigate().to(url);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("Unable to navigate to : " + url);
        }

    }
}

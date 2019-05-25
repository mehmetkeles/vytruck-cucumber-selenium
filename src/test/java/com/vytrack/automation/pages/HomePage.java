package com.vytrack.automation.pages;

import com.vytrack.automation.utils.Driver;
import com.vytrack.automation.utils.SeleniumUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    private static final Logger LOGGER = LogManager.getLogger(Driver.class);


    @FindBy(id = "pagetitle")
    private WebElement titleelement;

    public HomePage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    public void verifyTitleDisplays(String title) {
        SeleniumUtils.waitForPageToLoad(10);
        SeleniumUtils.waitForElement(titleelement);
        String actualTitle = titleelement.getText();
        try {
            Assert.assertEquals(title, actualTitle);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

    }

}

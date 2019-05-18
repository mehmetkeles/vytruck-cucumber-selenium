package com.vytrack.automation.hooks;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import com.vytrack.automation.utils.*;

public class Hook {
    private static final Logger logger = LogManager.getLogger();

    @Before
    public void suiteSetup(Scenario scenario) {
        Driver.initializeDriver();
        Driver.getDriver().manage().window().maximize();
        Driver.getDriver().manage().timeouts().implicitlyWait(Integer.valueOf(ConfigurationReader.getProperty("timeout")), TimeUnit.SECONDS);
        logger.debug("...........START AUTOMATION.............");
    }

    @After
    public void suiteTearDown(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                TakesScreenshot takesScreenshot = (TakesScreenshot) Driver.getDriver();
                byte[] image = takesScreenshot.getScreenshotAs(OutputType.BYTES);
                scenario.embed(image, "image/png");
            }
        } catch (WebDriverException e) {
            logger.debug(e.getMessage());
            logger.debug("WebDriverException found");
            throw new RuntimeException(e.getMessage());
        }
        Driver.closeDriver();
        logger.debug("...........END AUTOMATION.............");

    }
}

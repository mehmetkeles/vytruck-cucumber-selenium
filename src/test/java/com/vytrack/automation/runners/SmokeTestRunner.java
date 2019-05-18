package com.vytrack.automation.runners;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(

        plugin={"pretty:target/cucumber-pretty.txt",
                "html:target/html-report",
                "json:target/report.json",
                "junit:target/junit/junit-report.xml",
        },
        tags = {"~@ignore",},
        features= {
                "src/test/resources/features/SmokeTest.feature"
        },
        glue= {"com/vytrack/automation/stepdefinitions","com/vytrack/automation/hooks"},
//        dryRun=true,
        monochrome = true
)
public class SmokeTestRunner {

}

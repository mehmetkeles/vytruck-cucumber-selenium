package com.vytrack.automation.stepdefinitions;

import com.vytrack.automation.pages.HomePage;
import com.vytrack.automation.pages.LoginPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class LoginPageStepDefinition {
    LoginPage loginpage = new LoginPage();
    HomePage homepage = new HomePage();

    @Given("user is on the vytrack sign-in page")
    public void user_is_on_the_vytrack_sign_in_page() {
        loginpage.goToHomePage();
    }

    @Given("user logs in as an admin")
    public void user_logs_in_as_an_admin() {
        loginpage.login();
    }

    @Then("user verifies that {string} title displays")
    public void user_verifies_that_title_displays(String string) {
        homepage.verifyTitleDisplays(string);
    }
}

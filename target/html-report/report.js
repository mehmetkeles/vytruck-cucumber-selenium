$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("src/test/resources/features/SmokeTest.feature");
formatter.feature({
  "name": "Smoke test",
  "description": "",
  "keyword": "Feature"
});
formatter.scenario({
  "name": "Smoke test",
  "description": "",
  "keyword": "Scenario",
  "tags": [
    {
      "name": "@Smoke"
    }
  ]
});
formatter.before({
  "status": "passed"
});
formatter.step({
  "name": "user is on the vytrack sign-in page",
  "keyword": "Given "
});
formatter.match({
  "location": "LoginPageStepDefinition.user_is_on_the_vytrack_sign_in_page()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "user logs in as an admin",
  "keyword": "And "
});
formatter.match({
  "location": "LoginPageStepDefinition.user_logs_in_as_an_admin()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "user verifies that \"Fleet Management\" title displays",
  "keyword": "Then "
});
formatter.match({
  "location": "LoginPageStepDefinition.user_verifies_that_title_displays(String)"
});
formatter.result({
  "status": "passed"
});
formatter.after({
  "status": "passed"
});
});
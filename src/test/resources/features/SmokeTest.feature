Feature: Smoke test

  @Smoke
  Scenario: Smoke test
    Given user is on the vytrack sign-in page
    And user logs in as an admin
    Then user verifies that "Fleet Management" title displays
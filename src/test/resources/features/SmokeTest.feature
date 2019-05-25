Feature: Smoke test

  @Smoke
  Scenario: Smoke test
    Given user is on the nextbase sign-in page
    And user logs in as an admin
    Then user verifies that "Activity Stream" title displays
Feature: Home Page

  Background:

  Scenario: Access Home Page when user opens the http://www.phptravels.net/
    Given the user opens the browser
    When user access the url
    Then the Home Page should be displayed



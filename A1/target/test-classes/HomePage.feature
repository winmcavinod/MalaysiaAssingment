Feature: Testing Reqres Application

  Scenario Outline: Testing different request types and endpoints
    Given I am on the Reqres application homepage
    When I click on list user link on home page
    Then I get request and response result
    And I verify supportPage button is present
    And I click on supportpage and verify onetime and monthly report and upgrade details are present
    
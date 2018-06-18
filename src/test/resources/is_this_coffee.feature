@tag
Feature: Is this Coffee?
  I want to know if this is Coffee

  @tag1
  Scenario: Milk isn't Coffee
    Given I used only milk
    When I ask whether my drink is coffee
    Then I should be told "Nope"

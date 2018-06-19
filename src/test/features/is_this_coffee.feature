@FunctionalTest
Feature: Is this Coffee?
  I want to know if this is Coffee

  @FunctionalTest
  Scenario: Milk isn't Coffee
    Given I prepared a beverage
    And I used only milk
    When I ask whether my drink is coffee
    Then I should be told "Nope"

  @FunctionalTest
  Scenario: If I used beans it is Coffee
    Given I prepared a beverage
    And I used coffee beans
    When I ask whether my drink is coffee
    Then I should be told "Yes"
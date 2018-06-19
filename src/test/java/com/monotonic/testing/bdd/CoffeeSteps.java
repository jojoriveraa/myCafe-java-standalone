package com.monotonic.testing.bdd;

import static org.junit.Assert.assertEquals;

import com.monotonic.testing.m2.Cafe;
import com.monotonic.testing.m2.Coffee;
import com.monotonic.testing.m2.CoffeeType;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CoffeeSteps {

    private Cafe cafe = new Cafe();
    private Coffee coffee;

    private String actualAnswer;

    @And("^I used coffee beans$")
    public void iUsedCoffeeBeans() throws Throwable {
        coffee = cafe.brew(CoffeeType.ESPRESSO);
    }

    @And("^I used only milk$")
    public void iUsedOnlyMilk() throws Throwable {
        coffee = cafe.brew(CoffeeType.MILK);
    }

    @When("^I ask whether my drink is coffee$")
    public void iAskWhetherMyDrinkIsCoffee() throws Throwable {
        this.actualAnswer = MyCoffee.isThisCoffee(coffee);
    }

    @Then("^I should be told \"([^\"]*)\"$")
    public void iShouldBeTold(String expectedAnswer) throws Throwable {
        assertEquals(expectedAnswer, actualAnswer);
    }

    @Given("^I prepared a beverage$")
    public void iPreparedABeverage() throws Throwable {
        cafe.restockBeans(500);
        cafe.restockMilk(500);
    }

}

class MyCoffee {
    public static String isThisCoffee(Coffee coffee) {
        return coffee.isThisCoffee() ? "Yes" : "Nope";
    }
}

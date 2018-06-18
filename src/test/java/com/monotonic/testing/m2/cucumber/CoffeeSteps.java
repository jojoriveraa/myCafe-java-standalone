package com.monotonic.testing.m2.cucumber;


import static org.junit.Assert.assertEquals;

import com.monotonic.testing.m2.Cafe;
import com.monotonic.testing.m2.Coffee;
import com.monotonic.testing.m2.CoffeeType;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

class MyCoffee {
    public static String isThisCoffee(Coffee coffee) {
        return coffee.isThisCoffee() ? "Yes" : "Nope";
    }
}

public class CoffeeSteps {

    private Cafe cafe = new Cafe();
    private Coffee coffee;

    private String actualAnswer;

    @Given("^I used only milk$")
    public void iUsedOnlyMilk() throws Throwable {
        cafe.restockBeans(300);
        cafe.restockMilk(300);
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
}

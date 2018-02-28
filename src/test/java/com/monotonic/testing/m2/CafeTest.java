package com.monotonic.testing.m2;

import org.testng.annotations.Test;

import static com.monotonic.testing.m2.CoffeeType.ESPRESSO;
import static com.monotonic.testing.m2.CoffeeType.LATTE;
import static org.junit.Assert.assertEquals;

public class CafeTest {

    @Test
    public void canBrewEspresso() {
        // given
        Cafe cafe = new Cafe();
        cafe.restockBeans(7);

        // when
        Coffee coffee = cafe.brew(ESPRESSO);

        // then
        assertEquals(7, coffee.getBeans());
        assertEquals(0, coffee.getMilk());
        assertEquals(ESPRESSO, coffee.getType());
    }

    @Test
    public void brewingEspressoConsumesBeans() {
        // given
        Cafe cafe = new Cafe();
        cafe.restockBeans(7);

        // when
        cafe.brew(ESPRESSO);

        // then
        assertEquals(0, cafe.getBeansInStock());
    }

    @Test
    public void canBrewLatte() {
        // given
        Cafe cafe = new Cafe();
        cafe.restockBeans(7);
        cafe.restockMilk(227);

        // when
        Coffee coffee = cafe.brew(LATTE);

        // then
        assertEquals(LATTE, coffee.getType());
    }
    
    @Test
    public void brewingLatteConsumesBeans() {
        // given
        Cafe cafe = new Cafe();
        cafe.restockBeans(7);
        cafe.restockMilk(227);

        // when
        cafe.brew(LATTE);

        // then
        assertEquals(0, cafe.getBeansInStock());
    }
    
    @Test
    public void brewingLatteConsumesMilk() {
        // given
        Cafe cafe = new Cafe();
        cafe.restockBeans(7);
        cafe.restockMilk(227);

        // when
        cafe.brew(LATTE);

        // then
        assertEquals(0, cafe.getMilkInStock());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void mustRestockMilk() {
        // given
        Cafe cafe = new Cafe();

        // when
        cafe.restockMilk(0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void mustRestockBeans() {
        // given
        Cafe cafe = new Cafe();

        // when
        cafe.restockBeans(0);
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void lattesRequireMilk() {
        // given
        Cafe cafe = new Cafe();
        cafe.restockBeans(7);

        // when
        cafe.brew(LATTE);
    }

}

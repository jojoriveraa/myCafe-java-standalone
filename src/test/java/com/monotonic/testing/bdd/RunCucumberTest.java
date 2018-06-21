package com.monotonic.testing.bdd;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = { 
        "pretty", 
        "json:target/cucumber-reports/Cucumber.json",
        "junit:target/cucumber-reports/Cucumber.xml" }, 
    features = "src/test/features", 
    glue = "com.monotonic.testing.bdd")
public class RunCucumberTest {
}
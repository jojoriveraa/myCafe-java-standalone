package com.monotonic.testing.m2.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = { "pretty" }, 
        features = "src/test/resources", 
        glue = "com.monotonic.testing.m2.cucumber")
public class RunCucumberTest { // NOSONAR SonarLint does not know about @RunWith(Cucumber.class
}
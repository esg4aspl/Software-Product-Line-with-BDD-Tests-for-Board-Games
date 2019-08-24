package testing.testrunners;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features="./resources/features", glue="testing/stepdefinitions", plugin = {"pretty", "json:target/cuke-results.json"})
public class TestRunner {

}

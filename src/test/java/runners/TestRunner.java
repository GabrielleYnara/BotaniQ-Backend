package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features", //user stories
        glue = "definitions", //so it knows where to look for definitions of features
        plugin = {"pretty"}, //for nicely formatted printing output
        dryRun = true //maps the tests but doesn't run it
)
public class TestRunner {
}
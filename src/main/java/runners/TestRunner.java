package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/main/java/feature",
        glue = {"stepdefinitions"},
        monochrome = true,
        strict = true
)

public class TestRunner  {

}

package com.hill.web.cucumber.testRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.CucumberOptions.SnippetType;


@CucumberOptions(
        //result and report dir
        plugin = {"json:target/cucumber-results/cucumber-report.json", "html:target/cucumber-results"},
        //feature files path
        features = {"src/test/resources/features"},
        //steps & hooks classes implementation package
        glue = {"com/hill/web/cucumber/steps"},
        //filters tests by tags. ~ means exclude. "@one","@two" = "@one" & "@two"
        tags = {"@pagination and not(@wip)"},
        //check all steps and features without run
//        dryRun = true,
        //fail if step not implemented or skip it and go on
        strict = true,
        //unrealized steps template
        snippets = SnippetType.CAMELCASE //doesn't work
        //regex filter
//        name = "^Pagination allows.*"
)
public class RunCucumberTest extends AbstractTestNGCucumberTests {

}
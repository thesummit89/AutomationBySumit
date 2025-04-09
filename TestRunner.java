package com.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


    @CucumberOptions(
            features = "src/test/resources/features",
            glue = "src/test/java/com/stepDef",
            plugin = {
                    "pretty",
                    "json:target/cucumber.json",
                    "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" // ExtentReports Plugin
            },
            monochrome = true
    )
    public class TestRunner extends AbstractTestNGCucumberTests {


    }


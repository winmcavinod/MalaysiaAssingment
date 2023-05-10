package com.test.homePageTest;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;
@CucumberOptions(features = {"src/test/resources/HomePage.feature"},
glue = {"com.test.homePageTest"},
plugin = {"pretty"})
public class ReqresRunner extends AbstractTestNGCucumberTests{
	

}

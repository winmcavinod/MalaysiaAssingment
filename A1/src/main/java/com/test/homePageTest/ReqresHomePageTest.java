package com.test.homePageTest;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;

import io.cucumber.java.After;
//import dev.failsafe.internal.util.Assert;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ReqresHomePageTest {

	private WebDriver driver;

	@Before
	public void setUp() {

		String projectPath = System.getProperty("user.dir");
		System.out.println(projectPath);

		System.setProperty("webdriver.chrome.driver", projectPath + "/src/test/resources/chromedriver");
		driver = new ChromeDriver();
		// driver.manage().timeouts().implicitlyWait(10, null);
		driver.manage().window().maximize();
	}

	@Given("I am on the Reqres application homepage")
	public void i_am_on_the_reqres_application_homepage() {
		driver.get("https://reqres.in/");

		String expectedTitle = "Reqres - A hosted REST-API ready to respond to your AJAX requests";
		String actualTitle = driver.getTitle();
		try {
		    Assert.assertEquals(actualTitle, expectedTitle, "Home page title is not matching");
		    Reporter.log("Home page title is matching"+ "<br>");
		} catch (AssertionError e) {
		    Reporter.log("Assertion Failed: " + e.getMessage());
		    throw e;
		}

	}

	@When("I click on list user link on home page")
	public void I_click_on_list_user_link_on_home_page() {

		driver.findElement(By.xpath("//a[text()=' List users ']")).click();
	}

	@Then("I get request and response result")
	public void I_get_request_and_response_result() {

		try {
		// Find the element by XPath
		WebElement element = driver.findElement(By.xpath("//a[@data-key='request-output-link']"));
		// verify request box is present
		Assert.assertTrue(element.isDisplayed());
		Reporter.log("request box is present"+ "<br>");
		// verify link is present in request box
		WebElement element2 = driver.findElement(By.xpath("//span[contains(text(),'/api/users?page=2')]"));
		Assert.assertTrue(element2.isDisplayed());
		Reporter.log("link is present in request box"+ "<br>");
		// verify response box is present
		WebElement element3 = driver.findElement(By.xpath("//div[@class='response']"));
		Assert.assertTrue(element3.isDisplayed());
		// span[text()='200']
		Reporter.log("response box is present"+ "<br>");
		// verify response code 200 is present
		WebElement spanElement = driver
				.findElement(By.xpath("//span[@class='response-code' and @data-key='response-code']"));
		String spanText = spanElement.getText();
		System.out.println("Text from the span element: " + spanText);
		String expect = "200";
		String actual = spanText;
		Assert.assertEquals(expect, actual, "Response code 200 is not matching");
		}
		catch (AssertionError e) {
		    Reporter.log("Assertion Failed: " + e.getMessage());
		    throw e;
		}
	}

	@And("I verify supportPage button is present")
	public void I_verify_supportPage_button_is_present() {

		WebElement buttonElement = driver.findElement(By.xpath("//a[text()='Support ReqRes']"));
		boolean isButtonPresent = buttonElement.isDisplayed();
		Assert.assertTrue(isButtonPresent);
	}

	@And("I click on supportpage and verify onetime and monthly report and upgrade details are present")
	public void I_click_on_supportpage_and_verify_onetime_and_monthly_report_and_upgrade_details_are_present() {

		driver.findElement(By.xpath("//a[text()='Support ReqRes']")).click();
		WebElement oneTimeElement = driver.findElement(By.xpath("//label[text()='One-time payment ($)']"));
		// verify onetime payment is present
		Assert.assertTrue(oneTimeElement.isDisplayed());

		// verify monthly report is present
		WebElement monthlyElement = driver.findElement(By.xpath("//label[text()='Monthly support ($5/month)']"));
		Assert.assertTrue(monthlyElement.isDisplayed());

		// verify upgrade button is present
		WebElement upgradeElement = driver.findElement(By.xpath("//button[text()='Upgrade']"));
		Assert.assertTrue(upgradeElement.isDisplayed());

	}

	@Given("I click on single user not found")
	public void I_click_on_single_user_not_found() {

		driver.findElement(By.xpath("//a[text()=' Single user not found ']")).click();
	}
	
	@Then("I verify request and response")
	public void I_verify_request_and_response() throws InterruptedException {
		
		
		// Find the element by XPath
		WebElement element = driver.findElement(By.xpath("//a[@data-key='request-output-link']"));
		// verify request box is present
		Assert.assertTrue(element.isDisplayed());

		// verify link is present in request box
		WebElement element2 = driver.findElement(By.xpath("//span[contains(text(),'/api/users/23')]"));
		Assert.assertTrue(element2.isDisplayed());

		// verify response box is present
		WebElement element3 = driver.findElement(By.xpath("//div[@class='response']"));
		Assert.assertTrue(element3.isDisplayed());
		// span[text()='200']
		Thread.sleep(7000);
		// verify response code 404 is present
		WebElement spanElement1 = driver.findElement(By.xpath("//span[@data-key='response-code']"));
				
		String spanText1 = spanElement1.getText();
		System.out.println("Text from the span element: " + spanText1);
		String expect1 = "404";
		String actual1 = spanText1;
		Assert.assertEquals(expect1, actual1, "Response code 404 is not matching");

	}

	@After
	public void teardown() {

		driver.quit();
	}
}

package com.test.specAndSchemaTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class specandSchemaTest {
	
	
	private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // Set up the Chrome driver
    	String projectPath = System.getProperty("user.dir");
    	System.out.println(projectPath);
    	
        System.setProperty("webdriver.chrome.driver", projectPath+"/src/test/resources/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }
    @Test
    public void getUserById() {
        // Navigate to the reqres page
        driver.get("https://reqres.in");
        /*
         * read row 2 from output file 
         * read row 2 from position file
         * read row 2 from instrument file
         * verify row 2,3 & 2,4 in putputfile matches with row 2,2 & 2,3 in position file
         * find 2,3 row value of output file in instrument file and gets its corresponding unit price
         * 
         * 
         * 
         * 
         */
         
        
        
        
}
}
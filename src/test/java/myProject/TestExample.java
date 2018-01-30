package myProject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestExample {

	public WebDriver driver;
	
	@BeforeTest
	public void beforeTest() throws InterruptedException{
		System.setProperty("webdriver.chrome.driver", "chromedriver");
		driver = new ChromeDriver();
		driver.get("https://login.salesforce.com");
		
		Thread.sleep(5000);
		
		WebElement usernameField = driver.findElement(By.id("username"));
		usernameField.sendKeys("natalia.grechyanik@dev-1.com");
		WebElement passwordField = driver.findElement(By.id("password"));
		passwordField.sendKeys("pas5w0rd!");
		
		WebElement loginButton = driver.findElement(By.id("Login"));
		loginButton.click();
		
		Thread.sleep(10000);
	}
	
	@Test
	public void test_1(){
		System.out.println("test-1");
	
		
	}
	
	@AfterTest
	public void afterTest(){
		driver.quit();
	}


}

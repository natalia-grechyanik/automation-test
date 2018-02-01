package myProject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class TestExample {

	public WebDriver driver;

	@BeforeTest
	public void beforeTest() throws InterruptedException {

		String osName = System.getProperty("os.name");
		System.out.println(osName);

		if (osName == "Mac OS X") {
			System.setProperty("webdriver.chrome.driver", "chromedriver-linux");
			driver = new ChromeDriver();
		} else {
			FirefoxBinary firefoxBinary = new FirefoxBinary();
			firefoxBinary.addCommandLineOptions("--headless");
			System.setProperty("webdriver.gecko.driver", "geckodriver");
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			firefoxOptions.setBinary(firefoxBinary);
			driver = new FirefoxDriver(firefoxOptions);
		}
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
	public void test_1() {
		System.out.println("test-1");

	}

	@AfterTest
	public void afterTest() {
		System.out.println("####");
		driver.quit();
	}

}

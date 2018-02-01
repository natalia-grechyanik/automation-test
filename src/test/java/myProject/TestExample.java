package myProject;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
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
	public void beforeTest() throws InterruptedException, IOException {

		String osName = System.getProperty("os.name");
		System.out.println(osName);

		if (osName.equals("Mac OS X")) {
			System.setProperty("webdriver.chrome.driver", "chromedriver");
			driver = new ChromeDriver();
		} else if (osName.equals("Linux")) {
			System.setProperty("webdriver.chrome.driver", "chromedriver-linux");
			FirefoxBinary firefoxBinary = new FirefoxBinary();
			firefoxBinary.addCommandLineOptions("--headless");
			System.setProperty("webdriver.gecko.driver", "geckodriver");
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			firefoxOptions.setBinary(firefoxBinary);
			driver = new FirefoxDriver(firefoxOptions);
		}

		try {
			driver.get("https://login.salesforce.com");

			Thread.sleep(5000);

			WebElement usernameField = driver.findElement(By.id("username"));
			usernameField.sendKeys("natalia.grechyanik@dev-1.com");
			WebElement passwordField = driver.findElement(By.id("password"));
			passwordField.sendKeys("pas5w0rd!");

			WebElement loginButton = driver.findElement(By.id("Login"));
			loginButton.click();

			Thread.sleep(10000);
		} catch (Exception e) {
			takeScreenshot();
		}
	}

	@Test
	public void test_1() throws IOException {
		try {
			System.out.println("test-1");
			takeScreenshot();

		} catch (Exception e) {
			takeScreenshot();
		}
	}

	@AfterTest
	public void afterTest() {
		System.out.println("####");
		driver.quit();
	}

	public void takeScreenshot() throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		String scrnName = "scrn " + sdf.format(new Timestamp(System.currentTimeMillis())) + ".png";
		FileUtils.copyFile(scrFile, new File("screenshots/" + scrnName));
		System.out.println("Screenshot was saved - " + scrnName);
	}

}

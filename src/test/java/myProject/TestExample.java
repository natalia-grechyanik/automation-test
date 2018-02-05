package myProject;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.common.base.Predicate;

import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.LocatorConfig;

public class TestExample {

	public WebDriver driver;
	public static LocatorConfig locatorConfig = new LocatorConfig();

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

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

	}

	@Test
	public void test_1() throws Exception {
		try {
			System.out.println("test-1");

			// driver.get("https://login.salesforce.com");

			// Thread.sleep(5000);

			// WebElement usernameField = driver.findElement(By.id("username"));
			// usernameField.sendKeys("natalia.grechyanik@dev-1.com");
			// WebElement passwordField = driver.findElement(By.id("password"));
			// passwordField.sendKeys("pas5w0rd!");
			//
			// WebElement loginButton = driver.findElement(By.id("Login"));
			// loginButton.click();
			//
			// Thread.sleep(10000);

			driver.get("https://google.com");
			WebElement searchField = driver.findElement(By.id("lst-ib"));
			Actions builder = new Actions(driver);

			builder.sendKeys(driver.findElement(By.id("lst-ib")), "webdriver" + Keys.ENTER).build().perform();

			// ((JavascriptExecutor) driver).executeScript("window.open()");

			WebElement searhcDescription = driver
					.findElement(By.xpath("//*[@id='search']//*[@class='g'][1]//span[@class='st']"));
			builder.doubleClick(searhcDescription).build().perform();

			WebElement searchTitle = driver.findElement(By.xpath("//*[@id='search']//*[@class='g'][1]//h3/a"));
			searchTitle.sendKeys(Keys.CONTROL, Keys.RETURN);

			builder.sendKeys(Keys.PAGE_DOWN, Keys.RETURN).build().perform();

			builder.sendKeys(Keys.PAGE_UP, Keys.RETURN).build().perform();

			WebElement link = (new WebDriverWait(driver, 10))
					.until(ExpectedConditions.elementToBeClickable(By.linkText("Download")));
			link.click();

			// WebElement link = driver.findElement(By.xpath(""));
			// new
			// Actions(driver).keyDown(Keys.COMMAND).click(link).keyUp(Keys.COMMAND).build().perform();
			//
			// driver.findElement(By.cssSelector("body")).sendKeys(Keys.COMMAND
			// + "t");
			//
			// // new Actions(driver).sendKeys(Keys.CONTROL, "t").perform();
			// Keys.chord(Keys.CONTROL, "t");
			// Thread.sleep(10000);
			//
			// builder.sendKeys(Keys.chord(Keys.COMMAND, "t")).perform(); // вид
			// // комбинации
			//
			// Actions action = new Actions(driver);
			// action.keyDown(Keys.COMMAND).sendKeys("T").perform();
			//
			// String mainWindow = driver.getWindowHandle();
			// System.out.println(mainWindow);
			// Set<String> windows = driver.getWindowHandles();
			// System.out.println(windows.toString());
			// driver.switchTo().window(windows.toArray()[1].toString());
			// Thread.sleep(10000);
			// driver.get("https://google.com");

		} catch (Exception e) {
			takeScreenshot();
			throw e;
		}
	}

	@Test
	public void test_2() throws Exception {

		String searchWord = "documents";
		String tabName = "Articles";

		try {
			driver.get("https://help.salesforce.com");

			waitForPageLoading();

			System.out.println("Searching for " + searchWord);
			WebElement searchInputField = driver
					.findElement(By.xpath(locatorConfig.getLocator("globalHeader.searchInputField")));
			searchInputField.sendKeys(searchWord + Keys.ENTER);

			waitForPageLoading();

			WebElement pageContent = driver
					.findElement(By.xpath(locatorConfig.getLocator("pageContent.searchResults")));

			System.out.println("Selecting tab - " + tabName);
			WebElement tabItem = pageContent.findElement(
					By.xpath(String.format(locatorConfig.getLocator("pageContent.searchResults.tab"), tabName)));
			tabItem.click();

			waitForPageLoading();

			System.out.println("Selecting the first search result item - ");
			WebElement searchResult = pageContent
					.findElement(By.xpath(locatorConfig.getLocator("pageContent.searchResults.firstArticle")));
			String searchResultTitle = searchResult.getText();
			searchResult.click();

			waitForPageLoading();

			System.out.println("Verifying article title - " + searchResultTitle);
			WebElement articleContent = driver.findElement(By.id(locatorConfig.getLocator("articleContentId")));
			WebElement articleTitle = articleContent
					.findElement(By.xpath(locatorConfig.getLocator("articleContentId.articleTitle")));
			Assert.assertEquals(articleTitle.getText(), searchResultTitle);

			WebElement votePanel = articleContent
					.findElement(By.xpath(locatorConfig.getLocator("articleContentId.votePanel")));

			WebElement voteButton = null;

			List<WebElement> voteButtons = votePanel
					.findElements(By.xpath(locatorConfig.getLocator("articleContentId.votePanel.voteButtons")));
			if (voteButtons.size() == 2) {
				System.out.println("No one vote buttons was clicked");
				voteButton = voteButtons.get(0)
						.findElement(By.xpath(locatorConfig.getLocator("articleContentId.votePanel.voteButtons.like")));
			} else if (voteButtons.size() == 1) {
				voteButton = voteButtons.get(0).findElement(
						By.xpath(locatorConfig.getLocator("articleContentId.votePanel.voteButtons.button")));
			} else {
				System.out.println("Something is wrong with vote buttons");
				Assert.fail();
			}

			String className = voteButton.getAttribute("class");
			System.out.println("Clicking vote button - " + className);
			voteButton.click();

			waitForPageLoading();

			WebElement feedbackPanel = votePanel
					.findElement(By.xpath(locatorConfig.getLocator("articleContentId.votePanel.feedbackPanel")));
			WebElement textarea = feedbackPanel.findElement(
					By.xpath(locatorConfig.getLocator("articleContentId.votePanel.feedbackPanel.textarea")));
			WebElement submitButton = feedbackPanel.findElement(
					By.xpath(locatorConfig.getLocator("articleContentId.votePanel.feedbackPanel.submitButton")));

			String feedback = className.equals("like") ? "Very helpful" : "Not really helpful";
			System.out.println("Sending a message - " + feedback);
			textarea.sendKeys(feedback);
			submitButton.click();

			waitForPageLoading();

			System.out.println("Checking the message has been sent");
			WebElement confirmation = votePanel
					.findElement(By.xpath(locatorConfig.getLocator("articleContentId.votePanel.feedbackConfirmation")));
			Assert.assertEquals(confirmation.getText(), "Thank you for your feedback.");

		} catch (Exception e) {
			takeScreenshot();
			throw e;
		}
	}

	@AfterTest
	public void afterTest() {
		driver.quit();
	}

	
	private void takeScreenshot() throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		String scrnName = "scrn " + sdf.format(new Timestamp(System.currentTimeMillis())) + ".png";
		FileUtils.copyFile(scrFile, new File("screenshots/" + scrnName));
		System.out.println("Screenshot was saved - " + scrnName);
	}

	private void waitForPageLoading() {
		(new WebDriverWait(driver, 30)).until(
				ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(@class, 'slds-spinner')]")));
	}

	private void waitForSomething() {

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
		wait.pollingEvery(250, TimeUnit.MILLISECONDS);
		wait.withTimeout(20, TimeUnit.SECONDS);
		wait.ignoring(NoSuchElementException.class, TimeoutException.class);

		System.out.println("go");
		wait.until(new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver arg0) {
				try {
					List<WebElement> spinner = arg0
							// .findElement(By.xpath("//div[contains(@ng-if,'sldsSpinnerScope.showSpinner')]"));
							.findElements(By.xpath("//loading-spinner//slds-spinner"));
					if (spinner.size() > 0) {
						System.out.println("false");
						return false;
					}
					System.out.println("true");
					return true;
				} catch (TimeoutException e) {
					System.out.println("TimeoutException");
					return true;
				}
			}

		});
		System.out.println("finish");
	}
}

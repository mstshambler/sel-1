package tests;

import java.util.concurrent.TimeUnit;
import java.lang.Thread;
import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class FirstTest {
	private WebDriver chromeDriver;
	private WebDriver ieDriver;
	private WebDriver firefoxOldDriver;
	private WebDriver firefoxNewDriver;
	private WebDriver firefoxNightlyDriver;
	private WebDriverWait wait;

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
		
		chromeDriver = new ChromeDriver();
		chromeDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		wait = new WebDriverWait(chromeDriver, 10);
/*
		DesiredCapabilities caps = new DesiredCapabilities();
		
		System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
		System.setProperty("webdriver.ie.driver", "drivers/IEDriverServer.exe");
		System.setProperty("webdriver.gecko.driver", "drivers/geckodriver.exe");
		
		chromeDriver = new ChromeDriver();
		chromeDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		wait = new WebDriverWait(chromeDriver, 10);
		
		ieDriver = new InternetExplorerDriver();

		caps.setCapability(FirefoxDriver.MARIONETTE, false);
		firefoxOldDriver = new FirefoxDriver(caps);

		caps.setCapability(FirefoxDriver.MARIONETTE, true);
		firefoxNewDriver = new FirefoxDriver(caps);

		FirefoxBinary bin = new FirefoxBinary(new File("C:\\Program Files (x86)\\Nightly\\firefox.exe"));
		firefoxNightlyDriver = new FirefoxDriver(bin, new FirefoxProfile());
*/
	}
	
	@Test
	public void test1() throws Exception {
		chromeDriver.get("http://www.google.com");		
		Thread.sleep(5000);
	}

	@Test
	public void testLiteCart() throws Exception {
		chromeDriver.get("http://localhost:2220/litecart/admin/");
		wait.until(titleIs("My Store"));		
		chromeDriver.findElement(By.cssSelector("input[name=username]")).sendKeys("admin");
		chromeDriver.findElement(By.cssSelector("input[name=password]")).sendKeys("admin");
		chromeDriver.findElement(By.cssSelector("button[type=submit]")).click();
	}

	@After
	public void tearDown() throws Exception {
		chromeDriver.quit();
		chromeDriver = null;
		/*
		ieDriver.quit();
		ieDriver = null;
		
		firefoxOldDriver.quit();
		firefoxOldDriver = null;

		firefoxNewDriver.quit();
		firefoxNewDriver = null;

		firefoxNightlyDriver.quit();
		firefoxNightlyDriver = null;
		*/
	}
}

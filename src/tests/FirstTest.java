package tests;

import java.util.concurrent.TimeUnit;
import java.lang.Thread;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class FirstTest {
	private WebDriver driver;
	private WebDriverWait wait;

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 10);
	}
	
	@Test
	public void test1() throws Exception {
		driver.get("http://www.google.com");		
		Thread.sleep(5000);
	}

	@Test
	public void testLiteCart() throws Exception {
		driver.get("http://localhost:2220/litecart/admin/");
		wait.until(titleIs("My Store"));		
		driver.findElement(By.cssSelector("input[name=username]")).sendKeys("admin");
		driver.findElement(By.cssSelector("input[name=password]")).sendKeys("admin");
		driver.findElement(By.cssSelector("button[type=submit]")).click();
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		driver = null;
	}
}

package tests;

import java.lang.Thread;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import static org.junit.Assert.fail;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class BrowserLogTest {
	private WebDriver chromeDriver;
	private WebDriverWait wait;

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");

		chromeDriver = new ChromeDriver();
		//chromeDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		wait = new WebDriverWait(chromeDriver, 10);
		
	}

	boolean isElementPresent(WebElement elem, By locator) {
		try {
			elem.findElement(locator);
			return true;
		} catch (NoSuchElementException ex) {
			return false;
		}
	}

	@Test
	public void task17() throws Exception {
		List<WebElement> productList;
		int oldLogEntries;
		
		chromeDriver.get("http://localhost:2220/litecart/admin/");
		wait.until(titleIs("My Store"));		
		chromeDriver.findElement(By.cssSelector("input[name=username]")).sendKeys("admin");
		chromeDriver.findElement(By.cssSelector("input[name=password]")).sendKeys("admin");
		chromeDriver.findElement(By.cssSelector("button[type=submit]")).click();
		Thread.sleep(1000);

		chromeDriver.get("http://localhost:2220/litecart/admin/?app=catalog&doc=catalog&category_id=1");
		wait.until(titleIs("Catalog | My Store"));
		
		oldLogEntries = chromeDriver.manage().logs().get("browser").getAll().size();
		productList = chromeDriver.findElements(By.cssSelector("#content form .dataTable tr.row"));
		for (int i = 0; i < productList.size(); i++) {
			WebElement elem;
			
			// find product
			
			if (isElementPresent(productList.get(i), By.cssSelector("td input[type=checkbox]"))) {
				elem = productList.get(i).findElement(By.cssSelector("td input[type=checkbox]"));
				if (elem.getAttribute("name").toLowerCase().contains("products")) {
					productList.get(i).findElement(By.cssSelector("td a")).click();
					Thread.sleep(1000);
					
					// check log
					if (chromeDriver.manage().logs().get("browser").getAll().size() > oldLogEntries)
						fail("Log has some additional entries");
					
					// get back to categories
					chromeDriver.get("http://localhost:2220/litecart/admin/?app=catalog&doc=catalog&category_id=1");
					wait.until(titleIs("Catalog | My Store"));
					// we should ignore any messages appearing in catalog
					oldLogEntries = chromeDriver.manage().logs().get("browser").getAll().size();
					// reread products list to avoid stale element exception
					productList = chromeDriver.findElements(By.cssSelector("#content form .dataTable tr.row"));				
				}
			}
		}
	}
	
	@After
	public void tearDown() throws Exception {
		chromeDriver.quit();
		chromeDriver = null;
	}
}

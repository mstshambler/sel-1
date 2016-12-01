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

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import static org.junit.Assert.fail;

public class FindElementTest {
	private WebDriver chromeDriver;
	private WebDriverWait wait;

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");

		chromeDriver = new ChromeDriver();
		//chromeDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		wait = new WebDriverWait(chromeDriver, 10);
		
	}

	boolean isElementPresent(WebDriver driver, By locator) {
		try {
			driver.findElement(locator);
			return true;
		} catch (NoSuchElementException ex) {
			return false;
		}
	}
	
	@Test
	public void task7() throws Exception {
		int menu, submenu;
		int menuCount;
		List<WebElement> menuList, submenuList;
		
		chromeDriver.get("http://localhost:2220/litecart/admin/");
		wait.until(titleIs("My Store"));		
		chromeDriver.findElement(By.cssSelector("input[name=username]")).sendKeys("admin");
		chromeDriver.findElement(By.cssSelector("input[name=password]")).sendKeys("admin");
		chromeDriver.findElement(By.cssSelector("button[type=submit]")).click();
		Thread.sleep(1000);
		
		menuList = chromeDriver.findElements(By.cssSelector("#box-apps-menu > li > a"));
		menuCount = menuList.size();
		for(menu = 0; menu < menuCount; menu++) {
			menuList.get(menu).click();
			
			Thread.sleep(1000);			

			// assertTrue() could be used instead
			if (isElementPresent(chromeDriver, By.cssSelector("#content > h1")) == false)
				fail("No header");

			if (isElementPresent(chromeDriver, By.cssSelector("#box-apps-menu > li.selected > ul.docs"))) {
				submenuList = chromeDriver.findElements(By.cssSelector("#box-apps-menu > li.selected > ul.docs > li > a"));
				for(submenu = 1; submenu < submenuList.size(); submenu++) {
					submenuList.get(submenu).click();
					Thread.sleep(1000);			

					if (isElementPresent(chromeDriver, By.cssSelector("#content > h1")) == false)
						fail("No header");

					if (isElementPresent(chromeDriver, By.cssSelector("title")) == false)
						fail("No title");

					// rereading the list to avoid stale element exception
					submenuList = chromeDriver.findElements(By.cssSelector("#box-apps-menu > li.selected > ul.docs > li > a"));
				}
			}
			// rereading the list to avoid stale element exception
			menuList = chromeDriver.findElements(By.cssSelector("#box-apps-menu > li > a"));
		}
	}

	@Test
	public void task8() throws Exception {
		List<WebElement> productsList;
		int product;
		
		chromeDriver.get("http://localhost:2220/litecart");
		wait.until(titleIs("Online Store | My Store"));		

		productsList = chromeDriver.findElements(By.cssSelector(".product"));
		
		for (product = 0; product < productsList.size(); product++) {
			// assertTrue() could be used instead
			if (productsList.get(product).findElements(By.cssSelector(".sticker")).size() != 1)
				fail("Stickers not equal to 1");
		}
	}
	
	@After
	public void tearDown() throws Exception {
		chromeDriver.quit();
		chromeDriver = null;
	}
}

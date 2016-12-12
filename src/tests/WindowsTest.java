package tests;

import java.util.List;
import java.util.Set;

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
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfWindowsToBe;

public class WindowsTest {
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
	public void task14() throws Exception {
		String currentWindow;
		Set<String> oldWindows;
		List<WebElement> links;
		int i;
		
		chromeDriver.get("http://localhost:2220/litecart/admin/");
		wait.until(titleIs("My Store"));		
		chromeDriver.findElement(By.cssSelector("input[name=username]")).sendKeys("admin");
		chromeDriver.findElement(By.cssSelector("input[name=password]")).sendKeys("admin");
		chromeDriver.findElement(By.cssSelector("button[type=submit]")).click();
		wait.until(presenceOfElementLocated(By.cssSelector("#sidebar")));
		
		// get to "Add new country" page
		chromeDriver.get("http://localhost:2220/litecart/admin/?app=countries&doc=countries");
		wait.until(titleIs("Countries | My Store"));
		chromeDriver.findElement(By.cssSelector("#content > div a.button")).click();
		wait.until(titleIs("Add New Country | My Store"));
		
		// keep our current tabs
		currentWindow = chromeDriver.getWindowHandle();
		oldWindows = chromeDriver.getWindowHandles();
		
		// get through all links
		links = chromeDriver.findElements(By.cssSelector("a > i.fa-external-link"));
		for (i = 0; i < links.size(); i++) {
			Set<String> newWindows;
			
			// get parent node and click on it
			links.get(i).findElement(By.xpath("./..")).click();
			// wait for new window to open
			wait.until(numberOfWindowsToBe(oldWindows.size()+1));
			newWindows = chromeDriver.getWindowHandles();
			newWindows.removeAll(oldWindows);
			// get through all new windows and close them
			for (String newWindow : newWindows) {
				chromeDriver.switchTo().window(newWindow);
				chromeDriver.close();
			}
			// switch back
			chromeDriver.switchTo().window(currentWindow);
		}
	}
	 	
	@After
	public void tearDown() throws Exception {
		chromeDriver.quit();
		chromeDriver = null;
	}
}

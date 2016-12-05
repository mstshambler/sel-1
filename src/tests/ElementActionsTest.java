package tests;

import java.lang.Thread;
import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import static org.junit.Assert.fail;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class ElementActionsTest {
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
	public void task11() throws Exception {
		String fakeEmail;
		Calendar calendar = Calendar.getInstance();
		
		chromeDriver.get("http://localhost:2220/litecart");
		wait.until(titleIs("Online Store | My Store"));
		
		chromeDriver.findElement(By.cssSelector("#box-account-login .content a")).click();
		wait.until(titleIs("Create Account | My Store"));
		
		// create a fake email
		fakeEmail = "a" + Long.toString(calendar.getTime().getTime()) + "@nomail.plz";
		
		chromeDriver.findElement(By.cssSelector("#create-account .content input[name=firstname]")).sendKeys("Test");
		chromeDriver.findElement(By.cssSelector("#create-account .content input[name=lastname]")).sendKeys("User");
		chromeDriver.findElement(By.cssSelector("#create-account .content input[name=address1]")).sendKeys("Test Address");
		chromeDriver.findElement(By.cssSelector("#create-account .content input[name=postcode]")).sendKeys("655017");
		chromeDriver.findElement(By.cssSelector("#create-account .content input[name=city]")).sendKeys("Testulovo");
		chromeDriver.findElement(By.cssSelector("#create-account .content input[name=email]")).sendKeys(fakeEmail);
		chromeDriver.findElement(By.cssSelector("#create-account .content input[name=phone]")).sendKeys("+7123456789");
		chromeDriver.findElement(By.cssSelector("#create-account .content input[name=password]")).sendKeys("User");
		chromeDriver.findElement(By.cssSelector("#create-account .content input[name=confirmed_password]")).sendKeys("User");		
		// disable subscribing :)
		chromeDriver.findElement(By.cssSelector("#create-account .content input[name=newsletter]")).click();		
		chromeDriver.findElement(By.cssSelector("#create-account .content button[name=create_account]")).click();

		// Logout
		wait.until(titleIs("Online Store | My Store"));
		chromeDriver.findElements(By.cssSelector("#box-account .content a")).get(3).click();
		
		// Login
		chromeDriver.findElement(By.cssSelector("#box-account-login .content input[name=email]")).sendKeys(fakeEmail);
		chromeDriver.findElement(By.cssSelector("#box-account-login .content input[name=password]")).sendKeys("User");
		chromeDriver.findElement(By.cssSelector("#box-account-login .content button[name=login]")).click();
		Thread.sleep(1000);
		
		// Logout again
		chromeDriver.findElements(By.cssSelector("#box-account .content a")).get(3).click();
	}
	 
	@Test
	public void task12() throws Exception {
		String fakeName;
		Calendar calendar = Calendar.getInstance();
		List<WebElement> productList;
		int found;
		Select selectElement;
		
		// create a fake name
		fakeName = "Product_" + Long.toString(calendar.getTime().getTime());

		chromeDriver.get("http://localhost:2220/litecart/admin/");
		wait.until(titleIs("My Store"));		
		chromeDriver.findElement(By.cssSelector("input[name=username]")).sendKeys("admin");
		chromeDriver.findElement(By.cssSelector("input[name=password]")).sendKeys("admin");
		chromeDriver.findElement(By.cssSelector("button[type=submit]")).click();
		Thread.sleep(1000);

		//chromeDriver.get("http://localhost:2220/litecart/admin/?app=catalog&doc=catalog");
		chromeDriver.get("http://localhost:2220/litecart/admin/?category_id=0&app=catalog&doc=edit_product");
		wait.until(titleIs("Add New Product | My Store"));
		
		// General tab
		Thread.sleep(500);
		chromeDriver.findElement(By.cssSelector("#content .tabs .content #tab-general input[name=status]")).click();
		chromeDriver.findElement(By.cssSelector("#content .tabs .content #tab-general input[name=\"name[en]\"]")).sendKeys(fakeName);
		chromeDriver.findElement(By.cssSelector("#content .tabs .content #tab-general input[name=code]")).sendKeys(fakeName);
		chromeDriver.findElement(By.cssSelector("#content .tabs .content #tab-general input[name=\"product_groups[]\"][value=\"1-3\"]")).click();
		chromeDriver.findElement(By.cssSelector("#content .tabs .content #tab-general input[name=quantity]")).clear();
		chromeDriver.findElement(By.cssSelector("#content .tabs .content #tab-general input[name=quantity]")).sendKeys("1");

		// Information tab
		chromeDriver.findElement(By.cssSelector("#content .tabs .index a[href=\"#tab-information\"]")).click();
		Thread.sleep(500);
		
		selectElement = new Select(chromeDriver.findElement(By.cssSelector("#content .tabs .content #tab-information select[name=manufacturer_id]")));
		selectElement.selectByValue("1");
		selectElement = null;
		chromeDriver.findElement(By.cssSelector("#content .tabs .content #tab-information input[name=keywords]")).sendKeys("Test Product");
		chromeDriver.findElement(By.cssSelector("#content .tabs .content #tab-information input[name=\"short_description[en]\"")).sendKeys("Test Product");
		chromeDriver.findElement(By.cssSelector("#content .tabs .content #tab-information div.trumbowyg-editor")).sendKeys("Test Product");
		
		
		// Prices tab
		chromeDriver.findElement(By.cssSelector("#content .tabs .index a[href=\"#tab-prices\"]")).click();
		Thread.sleep(500);
		chromeDriver.findElement(By.cssSelector("#content .tabs .content #tab-prices input[name=purchase_price]")).clear();
		chromeDriver.findElement(By.cssSelector("#content .tabs .content #tab-prices input[name=purchase_price]")).sendKeys("10");
		chromeDriver.findElement(By.cssSelector("#content .tabs .content #tab-prices input[name=\"prices[USD]\"]")).clear();
		chromeDriver.findElement(By.cssSelector("#content .tabs .content #tab-prices input[name=\"prices[USD]\"]")).sendKeys("20");
		chromeDriver.findElement(By.cssSelector("#content .tabs .content #tab-prices input[name=\"prices[EUR]\"]")).clear();
		chromeDriver.findElement(By.cssSelector("#content .tabs .content #tab-prices input[name=\"prices[EUR]\"]")).sendKeys("15");

		selectElement = new Select(chromeDriver.findElement(By.cssSelector("#content .tabs .content #tab-prices select[name=purchase_price_currency_code]")));
		selectElement.selectByValue("EUR");
		selectElement = null;

		// Save and check if the product was saved successfully
		chromeDriver.findElement(By.cssSelector("#content form p .button-set button[name=save]")).click();
		Thread.sleep(1000);
		
		found = 0;
		productList = chromeDriver.findElements(By.cssSelector("#content form .dataTable tr.row"));
		for (int i = 0; i < productList.size(); i++) {
			if (fakeName.equals( productList.get(i).findElement(By.cssSelector("td a")).getAttribute("textContent") ))
				found = 1;
		}
		
		if (found == 0)
			fail("Test product wasn't created");
	}
	
	@After
	public void tearDown() throws Exception {
		chromeDriver.quit();
		chromeDriver = null;
	}
}

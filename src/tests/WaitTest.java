package tests;

import java.lang.Thread;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import static org.junit.Assert.fail;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class WaitTest {
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
	public void task13() throws Exception {
		chromeDriver.get("http://localhost:2220/litecart");
		wait.until(titleIs("Online Store | My Store"));
		int i;
		
		// pick 3 random products and add them to the cart
		for (i = 0; i < 3; i++) {
			chromeDriver.findElement(By.cssSelector(".product > a")).click();
			Thread.sleep(1000);

			// check if we have "size" option (yellow ducks have it)
			// it might be a good idea to run through all selects with "required" and pick first options available
			if ( isElementPresent(chromeDriver, By.cssSelector(".options select[name=\"options[Size]\"]")) ) {
				Select selectElement = new Select(chromeDriver.findElement(By.cssSelector(".options select[name=\"options[Size]\"")));
				selectElement.selectByIndex(1);
				selectElement = null;
			}
			
			// add the product to the cart and wait for the cart to refresh
			chromeDriver.findElement(By.cssSelector("button[name=add_cart_product]")).click();
			wait.until( ExpectedConditions.textToBe(By.cssSelector("#cart a.content span.quantity"), Integer.toString(i+1)) );

			// go back to the main page
			chromeDriver.get("http://localhost:2220/litecart");
			wait.until(titleIs("Online Store | My Store"));
		}
		
		// go to the cart to remove products
		chromeDriver.findElement(By.cssSelector("#cart > a")).click();
		wait.until(titleIs("Checkout | My Store"));

		i = 3; // set artificial limit
		// remove products while we have them in our cart
		while(i > 0) {
			int tableRows;
			
			// check how many rows we have in "Order summary"
			tableRows = chromeDriver.findElements(By.cssSelector("#checkout-summary-wrapper #box-checkout-summary .dataTable tr")).size();
			
			// check if we have shortcuts line
			if ( isElementPresent(chromeDriver, By.cssSelector("#box-checkout-cart ul.shortcuts li a")) ) {
				// click on the shortcut
				chromeDriver.findElement(By.cssSelector("#box-checkout-cart ul.shortcuts li a")).click();
				Thread.sleep(500);
			}
			// click on "remove" button
			chromeDriver.findElement(By.cssSelector("#box-checkout-cart button[name=remove_cart_item]")).click();
			// wait for the table to refresh (it should have less rows than before or the table should vanish)
			wait.until(ExpectedConditions.numberOfElementsToBeLessThan(By.cssSelector("#checkout-summary-wrapper #box-checkout-summary .dataTable tr"), tableRows));
			if (!isElementPresent(chromeDriver, By.cssSelector("#checkout-summary-wrapper #box-checkout-summary .dataTable")))
				break;
			i--;
		}
		if (i <= 0)
			fail("Too many products in cart. Remove isn't working");
	}
	 	
	@After
	public void tearDown() throws Exception {
		chromeDriver.quit();
		chromeDriver = null;
	}
}

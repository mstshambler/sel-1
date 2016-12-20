package tests.Task19.tests;

import java.lang.Thread;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import tests.Task19.pages.Task19MainPage;
import tests.Task19.pages.Task19Cart;
import tests.Task19.pages.Task19Product;

import static org.junit.Assert.fail;

public class Task19Test {
	private WebDriver chromeDriver;

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");

		chromeDriver = new ChromeDriver();
	}
	
	@Test
	public void task19() throws Exception {
		int i;
		// init pages
		Task19MainPage mainPage = new Task19MainPage(chromeDriver);
		Task19Cart cart = new Task19Cart(chromeDriver);
		Task19Product product = new Task19Product(chromeDriver);
		
		// open main page
		mainPage.OpenPage();
		
		// pick 3 random products and add them to the cart
		for (i = 0; i < 3; i++) {
			mainPage.ClickOnProduct();
			Thread.sleep(1000);
			
			// add current product to the cart
			product.SelectSize().AddToCart();
			cart.WaitForUpdate(i+1);

			// get back to the main page
			mainPage.OpenPage();
		}
		
		// let's get to the cart
		cart.OpenPage();		
		i = 3;
		while(i > 0) {
			// remove the product on top of our list and check if the list is empty
			if (cart.RemoveTopProduct().IsEmpty())
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

package tests.Task19.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class Task19Product extends Task19Page {
	public Task19Product(WebDriver d) {
		super(d);
	}
	
	public Task19Product AddToCart() {
		driver.findElement(By.cssSelector("button[name=add_cart_product]")).click();
		return this;
	}
	
	public Task19Product SelectSize() {
		if ( Task19Common.isElementPresent(driver, By.cssSelector(".options select[name=\"options[Size]\"]")) ) {
			Select selectElement = new Select(driver.findElement(By.cssSelector(".options select[name=\"options[Size]\"")));
			selectElement.selectByIndex(1);
			selectElement = null;
		}
		return this;
	}
}

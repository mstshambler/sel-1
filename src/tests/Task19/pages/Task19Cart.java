package tests.Task19.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfElementsToBeLessThan;

public class Task19Cart extends Task19Page {
	public Task19Cart(WebDriver d) {
		super(d);
	}
	
	public Task19Cart OpenPage() {
		driver.findElement(By.cssSelector("#cart > a")).click();
		wait.until(titleIs("Checkout | My Store"));
		return this;
	}
	
	public Task19Cart WaitForUpdate(Integer i) {
		wait.until( textToBe(By.cssSelector("#cart a.content span.quantity"), Integer.toString(i)) );
		return this;
	}
	
	public Task19Cart RemoveTopProduct() {
		int tableRows;
		
		tableRows = driver.findElements(By.cssSelector("#checkout-summary-wrapper #box-checkout-summary .dataTable tr")).size();
		if ( Task19Common.isElementPresent(driver, By.cssSelector("#box-checkout-cart ul.shortcuts li a")) ) {
			driver.findElement(By.cssSelector("#box-checkout-cart ul.shortcuts li a")).click();
			wait.until(elementToBeClickable(By.cssSelector("#box-checkout-cart button[name=remove_cart_item]")));
		}
		driver.findElement(By.cssSelector("#box-checkout-cart button[name=remove_cart_item]")).click();
		wait.until(numberOfElementsToBeLessThan(By.cssSelector("#checkout-summary-wrapper #box-checkout-summary .dataTable tr"), tableRows));
		return this;
	}
	
	public boolean IsEmpty() {
		return !Task19Common.isElementPresent(driver, By.cssSelector("#checkout-summary-wrapper #box-checkout-summary .dataTable"));
	}
}

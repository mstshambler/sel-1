package tests.Task19.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class Task19MainPage extends Task19Page {
	public Task19MainPage(WebDriver d) {
		super(d);
	}

	public Task19MainPage OpenPage() {
		driver.get("http://localhost:2220/litecart");
		wait.until(titleIs("Online Store | My Store"));
		return this;
	}
	
	public Task19MainPage ClickOnProduct() {
		driver.findElement(By.cssSelector(".product > a")).click();
		return this;
	}
}

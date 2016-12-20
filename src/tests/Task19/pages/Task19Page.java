package tests.Task19.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Task19Page {
	protected WebDriver driver;
	protected WebDriverWait wait;
	
	Task19Page(WebDriver d) {
		driver = d;
		wait = new WebDriverWait(driver, 10);
	}
}

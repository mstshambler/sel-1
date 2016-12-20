package tests.Task19.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class Task19Common {
	static boolean isElementPresent(WebDriver driver, By locator) {
		try {
			driver.findElement(locator);
			return true;
		} catch (NoSuchElementException ex) {
			return false;
		}
	}
}

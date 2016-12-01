package tests;

import java.lang.Thread;
import java.util.List;
import java.util.ArrayList;

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

public class GetAttributesTest {
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
	public void task9a() throws Exception {
		String lastCountry;
		List<WebElement> countriesList;
		List<String> countriesWithZonesList = new ArrayList<>();
		List<WebElement> zonesList;
		int country;
		
		chromeDriver.get("http://localhost:2220/litecart/admin/");
		wait.until(titleIs("My Store"));		
		chromeDriver.findElement(By.cssSelector("input[name=username]")).sendKeys("admin");
		chromeDriver.findElement(By.cssSelector("input[name=password]")).sendKeys("admin");
		chromeDriver.findElement(By.cssSelector("button[type=submit]")).click();
		Thread.sleep(1000);
		
		chromeDriver.get("http://localhost:2220/litecart/admin/?app=countries&doc=countries");
		Thread.sleep(1000);
		
		lastCountry = "";
		countriesList = chromeDriver.findElements(By.cssSelector(".dataTable .row"));
		for (country = 0; country < countriesList.size(); country++) {
			List<WebElement> countryInfo = countriesList.get(country).findElements(By.cssSelector("td"));
			WebElement href = countryInfo.get(4).findElement(By.cssSelector("a"));
			
			if (href.getAttribute("textContent").compareToIgnoreCase(lastCountry) < 0)
				fail("Countries aren't sorted");
			lastCountry = href.getAttribute("textContent");
			
			if (!countryInfo.get(5).getAttribute("textContent").equals("0"))
				countriesWithZonesList.add(href.getAttribute("href"));
		}
		
		for (country = 0; country < countriesWithZonesList.size(); country++) {
			String lastZone;
			int zone; 
			chromeDriver.get(countriesWithZonesList.get(country));
			Thread.sleep(1000);
			
			lastZone = "";
			zonesList = chromeDriver.findElements(By.cssSelector("#table-zones tr"));
			for(zone = 1; zone < zonesList.size() - 1; zone++) {
				List<WebElement> zoneInfo = zonesList.get(zone).findElements(By.cssSelector("td"));
				
				if (zoneInfo.get(2).getAttribute("textContent").compareToIgnoreCase(lastZone) < 0)
					fail("Zones aren't sorted");
				lastZone = zoneInfo.get(2).getAttribute("textContent");
			}
		}
		
	}

	@Test
	public void task9b() throws Exception {
		List<WebElement> countriesList;
		List<String> countriesWithZonesList = new ArrayList<>();
		List<WebElement> zonesList;
		int country;
		
		chromeDriver.get("http://localhost:2220/litecart/admin/");
		wait.until(titleIs("My Store"));		
		chromeDriver.findElement(By.cssSelector("input[name=username]")).sendKeys("admin");
		chromeDriver.findElement(By.cssSelector("input[name=password]")).sendKeys("admin");
		chromeDriver.findElement(By.cssSelector("button[type=submit]")).click();
		Thread.sleep(1000);
		
		
		chromeDriver.get("http://localhost:2220/litecart/admin/?app=geo_zones&doc=geo_zones");
		Thread.sleep(1000);
			
		countriesList = chromeDriver.findElements(By.cssSelector(".dataTable .row"));
		for (country = 0; country < countriesList.size(); country++) {
			List<WebElement> countryInfo = countriesList.get(country).findElements(By.cssSelector("td"));
			WebElement href = countryInfo.get(2).findElement(By.cssSelector("a"));			
			countriesWithZonesList.add(href.getAttribute("href"));
		}
		
		for (country = 0; country < countriesWithZonesList.size(); country++) {
			String lastZone;
			int zone; 
			chromeDriver.get(countriesWithZonesList.get(country));
			Thread.sleep(1000);
			
			lastZone = "";
			zonesList = chromeDriver.findElements(By.cssSelector("#table-zones td > select[name*=zone_code]"));
			for(zone = 0; zone < zonesList.size(); zone++) {
				if (zonesList.get(zone).getAttribute("outerText").compareToIgnoreCase(lastZone) < 0)
					fail("Zones aren't sorted");
				lastZone = zonesList.get(zone).getAttribute("outerText");
			}
		}
	}
	
	@Test
	public void task10() throws Exception {
		WebElement product;
		WebElement priceRegular, priceCampaign, productTitle;
		String priceRegularValue, priceCampaignValue, productTitleValue;
		
		chromeDriver.get("http://localhost:2220/litecart");
		wait.until(titleIs("Online Store | My Store"));		

		product = chromeDriver.findElement(By.cssSelector("#box-campaigns .product"));
		priceRegular = product.findElement(By.cssSelector(".regular-price"));
		priceCampaign = product.findElement(By.cssSelector(".campaign-price"));

		priceRegularValue = priceRegular.getAttribute("textContent");
		priceCampaignValue = priceCampaign.getAttribute("textContent");
		productTitleValue = product.findElement(By.cssSelector(".name")).getAttribute("textContent");
		
		if (!priceRegular.getCssValue("color").equals("rgba(119, 119, 119, 1)")
			|| !priceRegular.getCssValue("font-size").equals("14.4px") // 0.8em for 18px is 14.4
			|| !priceRegular.getCssValue("text-decoration").equals("line-through"))
			fail("Regular price CSS styles are wrong");
		if (!priceCampaign.getCssValue("color").equals("rgba(204, 0, 0, 1)")
			|| !priceCampaign.getCssValue("font-weight").equals("bold")
			|| !priceCampaign.getCssValue("font-size").equals("18px"))
			fail("Campaign price CSS styles are wrong");
		
		product.click();
		Thread.sleep(1000);

		productTitle = chromeDriver.findElement(By.cssSelector("#box-product h1.title"));
		priceRegular = chromeDriver.findElement(By.cssSelector("#box-product .content .information .regular-price"));
		priceCampaign = chromeDriver.findElement(By.cssSelector("#box-product .content .information .campaign-price"));
		
		if (!productTitle.getAttribute("textContent").equals(productTitleValue))
			fail("Product title is different");
		if (!priceRegular.getAttribute("textContent").equals(priceRegularValue))
			fail("Regular price is different");
		if (!priceCampaign.getAttribute("textContent").equals(priceCampaignValue))
			fail("Campaign price is different");

		if (!priceRegular.getCssValue("color").equals("rgba(102, 102, 102, 1)")
			|| !priceRegular.getCssValue("font-size").equals("16px")
			|| !priceRegular.getCssValue("text-decoration").equals("line-through"))
			fail("Regular price CSS styles in product pages are wrong");
		if (!priceCampaign.getCssValue("color").equals("rgba(204, 0, 0, 1)")
			|| !priceCampaign.getCssValue("font-weight").equals("bold")
			|| !priceCampaign.getCssValue("font-size").equals("22px"))
			fail("Campaign price CSS styles in product pages are wrong");
	}

	@After
	public void tearDown() throws Exception {
		chromeDriver.quit();
		chromeDriver = null;
	}
}

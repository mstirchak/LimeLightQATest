package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchResultsPage {
	public static WebElement element;
	public static List<WebElement> list;
	
	public static class NavBar{
		public static WebElement leftNavBar(WebDriver driver) {
			element = driver.findElement(By.xpath("//*[@id=\"leftNavContainer\"]"));
			return element;
		}
		
		public static WebElement Prices(WebDriver driver) {
			element = leftNavBar(driver).findElement(By.xpath("//*[contains(text(), 'Price')]"));
			return element;
		}
		
		public static WebElement lowPrice(WebDriver driver) {
			element = leftNavBar(driver).findElement(By.xpath("//*[@id='low-price']"));
			return element;
		}
		public static WebElement highPrice(WebDriver driver) {
			element = leftNavBar(driver).findElement(By.xpath("//*[@id='high-price']"));
			return element;
		}
		
		public static WebElement SubmitPriceRange(WebDriver driver) {
			element = driver.findElement(By.xpath("//*[@id=\"leftNavContainer\"]/ul[15]/div/li[6]/span/form/span[3]/span/input"));
			return element;
		}
	
	}
	
	public static class Results{
		
		public static List<WebElement> ResultList(WebDriver driver){
			list = driver.findElements(By.xpath("//li[starts-with(@id, 'result')]"));
			return list;
		}
		
		public static WebElement resultName(WebElement e) {
			element = e.findElement(By.xpath(".//div/div[3]/div[1]"));
			return element;
		}
		
		public static WebElement resultPrice(WebElement e) {
			element = e.findElement(By.xpath(".//div/div[4]/div[1]"));
			return element;
		}
		
		public static WebElement resultStars(WebElement e) {
			element = e.findElement(By.xpath(".//div/div[6]/span/span/a"));
			return element;
		}

		
	}

}

package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {
	public static WebElement element;
	
	public static WebElement mainSearch(WebDriver driver){
		element = driver.findElement(By.xpath("//*[@id=\"twotabsearchtextbox\"]"));
		return element;
	}
	
	public static WebElement searchButton(WebDriver driver) {
		element = driver.findElement(By.xpath("//*[@id=\"nav-search\"]/form/div[2]/div/input"));
		return element;
	}
	
}

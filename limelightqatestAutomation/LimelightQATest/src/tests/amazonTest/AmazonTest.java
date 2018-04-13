package tests.amazonTest;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pages.HomePage;
import pages.SearchResultsPage;
import selenium.DriverSetup;


public class AmazonTest {

	public static WebDriver driver;
	public static WebDriverWait wait;
	
	
	@BeforeClass(alwaysRun = true)
	public void setupClass()
	{
		//Set Up Drivers
		driver = DriverSetup.setupDriver(DriverSetup.Browser.Chrome, "chromedriver");
		wait = new WebDriverWait(driver, 10);
	}
	
	@BeforeMethod(alwaysRun = true)
	public void setupTest()
	{
		//navigate to Amazon.come
		driver.get("https://www.amazon.com");
	}

	@Parameters()
	@Test(description = "Search for iPad Air 2 Cases")
	public void groupSetup() throws Exception{

		//Search for "ipad air 2 case"
		WebElement search = HomePage.mainSearch(driver);
		search.sendKeys("ipad air 2 case");
		WebElement submit = HomePage.searchButton(driver);
		submit.click();
		
		//Refine Search for Plastic Cases Only
		WebElement leftNav = SearchResultsPage.NavBar.leftNavBar(driver);
		WebElement material = leftNav.findElement(By.xpath("//*[contains(text(), 'Case Material')]"));
		WebElement plastic = material.findElement(By.xpath("//*[contains(text(), 'Plastic')]"));
		plastic.click();
		
		//LeftNavBar goes stale at this point - must wait and refresh the element
		wait.until(ExpectedConditions.stalenessOf(leftNav));
		leftNav = SearchResultsPage.NavBar.leftNavBar(driver);
		
		//Refine search to cases between $20 - $100
		WebElement low_price = SearchResultsPage.NavBar.lowPrice(driver);
		WebElement high_price = SearchResultsPage.NavBar.highPrice(driver);
		WebElement rangeSubmit = SearchResultsPage.NavBar.SubmitPriceRange(driver);
		
		low_price.sendKeys("20");
		high_price.sendKeys("100");
		rangeSubmit.click();
		
		//Create a L=WebElement List of the refined Results
		List<WebElement> Results = driver.findElements(By.xpath("//li[starts-with(@id, 'result')]"));
		
		//Iterate through first 5 results, output Names and Prices and Ratings
		WebElement name, price, stars; 
		int count = 0;
		boolean isValidPriceRange = true;
		for(WebElement e : Results) {
			count++;
			if(count > 5) break;
			name = SearchResultsPage.Results.resultName(e);
			price = SearchResultsPage.Results.resultPrice(e);
			double dPrice = AmazonHelpers.cleanPrice(price.getText());
			if(dPrice < 20 || dPrice > 100) {
				isValidPriceRange = false;		//if price not between $20 - $100, make flag false
			}
			stars = SearchResultsPage.Results.resultStars(e);
			System.out.printf("Name: %s%nPrice: %f%nRating: %f%n", name.getAttribute("textContent"),AmazonHelpers.cleanPrice(price.getAttribute("textContent")),
					AmazonHelpers.cleanStars(stars.getAttribute("textContent")));
		}
		
		//Assert that the first 5 results are between $20 - $100
		Assert.assertTrue(isValidPriceRange);
		
		//Sort the first 5 results by price 
		List<WebElement> SortedByPrice = AmazonHelpers.sortResultsBy(Results, "price");
		
		//Sort the first 5 results by Score/Rating
		List<WebElement> SortedByRating = AmazonHelpers.sortResultsBy(Results, "rating");
		
		//Assert that the first 5 elements are sorted by price
		boolean isOrderedByPrice = true;
		for(int i = 0; i < 4; i++) {
			double currentPrice = AmazonHelpers.cleanPrice(SearchResultsPage.Results.resultPrice(SortedByPrice.get(i)).getText());
			double nextPrice = AmazonHelpers.cleanPrice(SearchResultsPage.Results.resultPrice(SortedByPrice.get(i + 1)).getText());
			if(nextPrice < currentPrice) {
				isOrderedByPrice = false;
			}
		}
		Assert.assertTrue(isOrderedByPrice);
		
		//Recommend a purchase based on Cost and Score
		WebElement Recommended_Purchase = Results.get(0);
		int counter = 0;
		double score = 0;
		double rating ,pr;
		for(WebElement e :  Results) {
			if(counter > 5) break;
			counter++;
			pr = (AmazonHelpers.cleanPrice(SearchResultsPage.Results.resultPrice(e).getText())) / 100;
			rating = (AmazonHelpers.cleanStars(SearchResultsPage.Results.resultStars(e).getAttribute("textContent"))) / 5 ;
			if((pr + rating) > score) {
				Recommended_Purchase = e;
				score = pr + rating;
			}
		}
		
		System.out.printf("\nRecommended Purchase Based on Cost and Score:\nName:%s\nPrice: $%s\nScore:%s\n",
				SearchResultsPage.Results.resultName(Recommended_Purchase).getText(), 
				AmazonHelpers.cleanPrice(SearchResultsPage.Results.resultPrice(Recommended_Purchase).getText()),
				SearchResultsPage.Results.resultStars(Recommended_Purchase).getAttribute("textContent"));
	}
	
	@AfterMethod(alwaysRun = true)
	public void tearDownTest()
	{
		driver.get("https://www.amazon.com");
	}
	
	@AfterClass(alwaysRun = true)
	public void tearDownClass()
	{
		driver.close();
	}
	
	
	
}

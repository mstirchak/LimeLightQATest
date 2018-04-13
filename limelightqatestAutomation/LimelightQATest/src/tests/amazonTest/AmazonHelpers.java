package tests.amazonTest;

import java.util.List;

import org.openqa.selenium.WebElement;

import pages.SearchResultsPage;

public class AmazonHelpers {
	
	public static double cleanPrice(String price) {
		//remove $ sign
		if(Character.isSpaceChar(price.charAt(1))) {
			price = price.substring(2,6);
		}
		else price = price.substring(1,6);
		//add decimal point
		price = price.replace(" ", ".");
		double result = Double.parseDouble(price);
		return result;
	}
	
	public static double cleanStars(String stars) {
		//remove extra chars
		stars = stars.substring(0, 2);
		double result = Double.parseDouble(stars);
		return result;
	}
	
	public static List<WebElement> sortResultsBy(List<WebElement> list, String str) {
		WebElement cur;
		int index;
		if(str.equals("price")) {
			for(int i = 1; i < 5; i++) {
				cur = list.get(i);
				index = i;
				for(int j = index - 1; j>= 0; j-- ) {
					if(cleanPrice((SearchResultsPage.Results.resultPrice(list.get(j)).getText())) > 
							cleanPrice(SearchResultsPage.Results.resultPrice(cur).getText())) {
						index = j;
						list.set(j + 1, list.get(j));
					}
					else break;
				}
				if(index != i) {
					list.set(index, cur);
				}
			}
			
		}
		else if(str.equals("rating")){
			for(int i = 1; i < 5; i++) {
				cur = list.get(i);
				index = i;
				for(int j = index - 1; j>= 0; j-- ) {
					if(cleanStars((SearchResultsPage.Results.resultStars(list.get(j)).getAttribute("textContent"))) > 
							cleanStars(SearchResultsPage.Results.resultStars(cur).getAttribute("textContent"))) {
						index = j;
						list.set(j + 1, list.get(j));
					}
					else break;
				}
				if(index != i) {
					list.set(index, cur);
				}
			}
		}
		return list;
	}
}

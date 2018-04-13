package selenium;

import java.io.File;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import java.sql.Time;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class DriverSetup {

	static String pathToDriver = Paths.get("").toAbsolutePath().toString() +
			File.separator + "driverExecutables" + File.separator;
    public enum Browser {
        Chrome,
        IE,
        Firefox
    }
    
	public static WebDriver setupDriver(Browser browser, String driverFileName) {
		WebDriver driver = null;
		String osName = (System.getProperty("os.name").toLowerCase().contains("mac") ? "mac" : "windows");
		if(browser == Browser.Chrome) {
			if (osName.equals("windows")) {
				System.setProperty("webdriver.chrome.driver", pathToDriver + driverFileName);
			} else {
				System.setProperty("webdriver.chrome.driver", pathToDriver + driverFileName);
			}
			driver = new ChromeDriver();
		}
		
		else if(browser == Browser.Firefox) {
			if (osName.equals("windows")) {
				System.setProperty("webdriver.firefox.driver", pathToDriver + driverFileName);
			} else {
				System.setProperty("webdriver.firefox.driver", pathToDriver + driverFileName);
			}
			driver = new FirefoxDriver();
		}
		
		else if(browser == Browser.IE) {
			if (osName.equals("windows")) {
				System.setProperty("webdriver.ie.driver", pathToDriver + driverFileName);
			} else {
				System.out.printf("Error: Internet Explorer can only be used on Windows!\n");
			}
			driver = new InternetExplorerDriver();
		}
		
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		return driver;
	}
	
}

package com.dynamite.shopping.utils.Driver;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public final class BrowserDriver implements ImprovedDriver {

	private WebDriver driver;
	private JavascriptExecutor jsExecutor;
	private WebDriverWait wait;
	private final String browserName;
	private final String chromeDriverPath = "./src/main/resources/drivers/chromedriver.exe";
	public static Logger log = Logger.getLogger("errorLogger");
	
	public BrowserDriver(String browserName) {
		this.browserName = browserName;
		createDriver(browserName);
		this.wait = new WebDriverWait(driver, 60);
		this.jsExecutor = (JavascriptExecutor) driver;
	}

	private void createDriver(String browserName)
	{
		switch (browserName.toUpperCase()) {
			case "FIREFOX":
				createFirefoxDriver();
				break;
		
			case "CHROME":
				createChromeDriver();
				break;
				
			default:
				throw new IllegalArgumentException ("invalid browser name");
		}				
	}
	
	private void createChromeDriver() {
		
		File chromeDriverFile = new File(chromeDriverPath);
		if (!chromeDriverFile.exists())
			throw new RuntimeException("chrome driver does not exist on " + 
		                                chromeDriverPath);
		
		try {
			System.setProperty("webdriver.chrome.driver", chromeDriverPath);
			this.driver = new ChromeDriver();
		}
		catch (Exception ex) {
			throw new RuntimeException("could not create the chrome driver");
		}
	}
	
	private void createFirefoxDriver() {
		try {
			this.driver = new FirefoxDriver();
		}
		catch (Exception ex) {
			throw new RuntimeException("could not create the firefox driver");
		}
	}
	
	@Override
	public String toString() {
		return this.browserName;
	}
	
	public WebDriver getWrappedDriver() {
		return this.driver;
	}

	@Override
	public void close() {
		driver.close();		
	}

	@Override
	public WebElement findElement(By locator) {
		WebElement element = wait.until(visibilityOfElementLocated(locator));
		return element;
	}

	@Override
	public List<WebElement> findElements(By arg0) {
		return driver.findElements(arg0);
	}

	@Override
	public void get(String arg0) {
		driver.get(arg0);		
	}

	@Override
	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	@Override
	public String getPageSource() {		
		return driver.getPageSource();
	}

	@Override
	public String getTitle() {
		return driver.getTitle();
	}

	@Override
	public String getWindowHandle() {
		return driver.getWindowHandle();
	}

	@Override
	public Set<String> getWindowHandles() {
		return driver.getWindowHandles();
	}

	@Override
	public Options manage() {
		return driver.manage();
	}

	@Override
	public Navigation navigate() {
		return driver.navigate();
	}

	@Override
	public void quit() {
		driver.quit();		
	}

	@Override
	public TargetLocator switchTo() {
		return driver.switchTo();
	}

	@Override
	public WebElement findClickableElement(By locator) {
		WebElement element = wait.until(elementToBeClickable(locator));
		return element;
	}
	
	@Override
	public WebElement findVisibleElement(By locator) {
		WebElement element = wait.until(visibilityOfElementLocated(locator));
		return element;
	}

	@Override
	public WebElement findHiddenElement(By locator) {
		WebElement element = wait.until(presenceOfElementLocated(locator));
		return element;
	}
	
	@Override
	public void waitUntilVisible(WebElement element) {
		try {
			wait.until(visibilityOf(element));
		}
		catch (Exception ex) {
			throw new RuntimeException(element.toString() + " is still visible");		
		}
	}
	
	@Override
	public void waitUntilElementIncludesValue(WebElement element, String text) {
		try {
			wait.until(textToBePresentInElement(element, text));
		}
		catch (Exception ex) {
			throw new RuntimeException(text + " is not included in " + element.toString());		
		}		
	}	


	@Override
	public Boolean titleIs(String title) {
		try {
			return wait.until(ExpectedConditions.titleIs(title));
		}
		catch (Exception ex) {
			log.fatal(ex.getMessage());
			return false;
		}
		
	}
	
	@Override
	public Boolean titleContains(String title) {
		try {
			return wait.until(ExpectedConditions.titleContains(title));
		}
		catch (Exception ex) {
			log.fatal(ex.getMessage());
			return false;
		}
	}

	@Override
	public Boolean urlIs(String url) {
		try {
			return wait.until(ExpectedConditions.urlToBe(url));
		}
		catch (Exception ex) {
			log.fatal(ex.getMessage());
			return false;
		}
	}

	@Override
	public Boolean urlContains(String url) {
		try {
			return wait.until(ExpectedConditions.urlContains(url));
		}
		catch (Exception ex) {
			log.fatal(ex.getMessage());
			return false;
		}
	}	
	
	@Override
	public void executeJS(String jsCommand) {
		try {
			jsExecutor.executeScript(jsCommand);
		}
		catch (Exception ex) {
			log.fatal(ex.getMessage());		
		}
	}

	@Override
	public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
		return ((TakesScreenshot) driver).getScreenshotAs(target);
	}

}

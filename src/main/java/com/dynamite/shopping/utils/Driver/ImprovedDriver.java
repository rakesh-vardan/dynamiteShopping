package com.dynamite.shopping.utils.Driver;

import org.openqa.selenium.By;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface ImprovedDriver  extends WebDriver, TakesScreenshot {
	WebElement findClickableElement(By locator);
	WebElement findVisibleElement(By locator);
	WebElement findHiddenElement(By locator);
	Boolean titleIs(String title);
	Boolean titleContains(String title);
	Boolean urlIs(String url);
	Boolean urlContains(String url);
	void executeJS(String jsCommand);
	void waitUntilVisible(WebElement element);
	void waitUntilElementIncludesValue(WebElement element, String text);
}

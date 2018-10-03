package com.dynamite.shopping.element;

import org.openqa.selenium.*;
import org.openqa.selenium.internal.WrapsElement;

import java.util.List;

public abstract class TypifiedElement implements WrapsElement, Named, WebElement {
    private final WebElement wrappedElement;
    private String name;

   protected TypifiedElement(WebElement wrappedElement) {
        this.wrappedElement = wrappedElement;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    @SuppressWarnings("squid:S1166")
    // Sonar "Exception handlers should preserve the original exception" rule
    public boolean exists() {
        try {
            getWrappedElement().isDisplayed();
        } catch (NoSuchElementException | TimeoutException ignored) {
            return false;
        }
        return true;
    }

    public WebElement getWrappedElement() {
        return wrappedElement;
    }

    public void click() {
        getWrappedElement().click();
    }

    public void submit() {
        getWrappedElement().submit();
    }

    public void sendKeys(CharSequence... keysToSend) {
        getWrappedElement().sendKeys(keysToSend);
    }

    
    public void clear() {
        getWrappedElement().clear();
    }

    
    public String getTagName() {
        return getWrappedElement().getTagName();
    }

    
    public String getAttribute(String name) {
        return getWrappedElement().getAttribute(name);
    }

    
    public boolean isSelected() {
        return getWrappedElement().isSelected();
    }

    
    public boolean isEnabled() {
        return getWrappedElement().isEnabled();
    }

    
    public String getText() {
        return getWrappedElement().getText();
    }

    
    public List<WebElement> findElements(By by) {
        return getWrappedElement().findElements(by);
    }

    
    public WebElement findElement(By by) {
        return getWrappedElement().findElement(by);
    }

    
    public boolean isDisplayed() {
        return getWrappedElement().isDisplayed();
    }

    
    public Point getLocation() {
        return getWrappedElement().getLocation();
    }

    
    public Dimension getSize() {
        return getWrappedElement().getSize();
    }

    
    public Rectangle getRect() {
        return getWrappedElement().getRect();
    }

    
    public String getCssValue(String propertyName) {
        return getWrappedElement().getCssValue(propertyName);
    }

    
    public <X> X getScreenshotAs(OutputType<X> target) {
        return getWrappedElement().getScreenshotAs(target);
    }
}

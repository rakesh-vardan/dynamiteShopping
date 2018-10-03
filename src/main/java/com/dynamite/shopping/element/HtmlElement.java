package com.dynamite.shopping.element;

import org.openqa.selenium.*;
import org.openqa.selenium.internal.WrapsElement;

import java.util.List;

public class HtmlElement implements WebElement, WrapsElement, Named {
    private WebElement wrappedElement;
    private String name;

    public WebElement getWrappedElement() {
        return wrappedElement;
    }

    public void setWrappedElement(WebElement wrappedElement) {
        this.wrappedElement = wrappedElement;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SuppressWarnings("squid:S1166")  
    public boolean exists() {
        try {
            getWrappedElement().isDisplayed();
        } catch (NoSuchElementException | TimeoutException ignored) {
            return false;
        }
        return true;
    }

    public void click() {
        wrappedElement.click();
    }

    public void submit() {
        wrappedElement.submit();
    }

    public void sendKeys(CharSequence... charSequences) {
        wrappedElement.sendKeys(charSequences);
    }

    public void clear() {
        wrappedElement.clear();
    }

    public String getTagName() {
        return wrappedElement.getTagName();
    }

    public String getAttribute(String name) {
        return wrappedElement.getAttribute(name);
    }

    public boolean isSelected() {
        return wrappedElement.isSelected();
    }

    public boolean isEnabled() {
        return wrappedElement.isEnabled();
    }

    public String getText() {
        return wrappedElement.getText();
    }

    public List<WebElement> findElements(By by) {
        return wrappedElement.findElements(by);
    }

    public WebElement findElement(By by) {
        return wrappedElement.findElement(by);
    }

    public boolean isDisplayed() {
        return wrappedElement.isDisplayed();
    }

    public Point getLocation() {
        return wrappedElement.getLocation();
    }

    public Dimension getSize() {
        return wrappedElement.getSize();
    }

    public Rectangle getRect() {
        return wrappedElement.getRect();
    }

    public String getCssValue(String name) {
        return wrappedElement.getCssValue(name);
    }

    public String toString() {
        return name;
    }

    public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
        return wrappedElement.getScreenshotAs(outputType);
    }
}

package com.dynamite.shopping.element;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class CheckBox extends TypifiedElement {
   
    public CheckBox(WebElement wrappedElement) {
        super(wrappedElement);
    }

    public WebElement getLabel() {
        try {
            return getWrappedElement().findElement(By.xpath("following-sibling::label"));
        } catch (NoSuchElementException e) {
            return null;
        }
    }

   public String getLabelText() {
        WebElement label = getLabel();
        return label == null ? null : label.getText();
    }

    public String getText() {
        return getLabelText();
    }

    public void select() {
        if (!isSelected()) {
            getWrappedElement().click();
        }
    }

   public void deselect() {
        if (isSelected()) {
            getWrappedElement().click();
        }
    }

   public void set(boolean value) {
        if (value) {
            select();
        } else {
            deselect();
        }
    }
}

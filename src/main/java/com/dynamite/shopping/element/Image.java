package com.dynamite.shopping.element;

import org.openqa.selenium.WebElement;

public class Image extends TypifiedElement {

    public Image(WebElement wrappedElement) {
        super(wrappedElement);
    }

    public String getSource() {
        return getWrappedElement().getAttribute("src");
    }

   
    public String getAlt() {
        return getWrappedElement().getAttribute("alt");
    }

}

package com.dynamite.shopping.element;

import org.openqa.selenium.WebElement;


public class Link extends TypifiedElement {
    public Link(WebElement wrappedElement) {
        super(wrappedElement);
    }

    
    public String getReference() {
        return getWrappedElement().getAttribute("href");
    }
}

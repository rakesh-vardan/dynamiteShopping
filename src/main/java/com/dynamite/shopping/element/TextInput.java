package com.dynamite.shopping.element;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.util.Optional;

public class TextInput extends TypifiedElement {
   
    public TextInput(WebElement wrappedElement) {
        super(wrappedElement);
    }

    @Deprecated
    public String getEnteredText() {
        return getText();
    }

    
    @Override
    public String getText() {
        if ("textarea".equals(getWrappedElement().getTagName())) {
            return getWrappedElement().getText();
        }

        return Optional.ofNullable(getWrappedElement().getAttribute("value")).orElse("");
    }

    
    public String getClearCharSequence() {
        return StringUtils.repeat(Keys.DELETE.toString() + Keys.BACK_SPACE, getText().length());
    }
}

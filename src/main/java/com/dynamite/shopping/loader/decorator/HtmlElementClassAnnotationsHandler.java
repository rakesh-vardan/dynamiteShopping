package com.dynamite.shopping.loader.decorator;

import com.dynamite.shopping.element.HtmlElement;
import com.dynamite.shopping.exceptions.HtmlElementsException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.pagefactory.AbstractAnnotations;

public class HtmlElementClassAnnotationsHandler<T extends HtmlElement> extends AbstractAnnotations {
    private final Class<T> elementClass;

    public HtmlElementClassAnnotationsHandler(Class<T> elementClass) {
        this.elementClass = elementClass;
    }

    @Override
    public By buildBy() {
        Class<?> clazz = elementClass;
        while (clazz != Object.class) {
            if (clazz.isAnnotationPresent(FindBy.class)) {
                return buildByFromFindBy(clazz.getAnnotation(FindBy.class));
            }
            clazz = clazz.getSuperclass();
        }

        throw new HtmlElementsException(String.format("Cannot determine how to locate instance of %s", elementClass));
    }

    @Override
    public boolean isLookupCached() {
        return false;
    }
}

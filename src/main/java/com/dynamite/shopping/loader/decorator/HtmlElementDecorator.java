package com.dynamite.shopping.loader.decorator;

import com.dynamite.shopping.element.HtmlElement;
import com.dynamite.shopping.element.TypifiedElement;
import com.dynamite.shopping.loader.decorator.proxyhandlers.HtmlElementListNamedProxyHandler;
import com.dynamite.shopping.loader.decorator.proxyhandlers.TypifiedElementListNamedProxyHandler;
import com.dynamite.shopping.loader.decorator.proxyhandlers.WebElementListNamedProxyHandler;
import com.dynamite.shopping.loader.decorator.proxyhandlers.WebElementNamedProxyHandler;
import com.dynamite.shopping.pagefactory.CustomElementLocatorFactory;
import com.dynamite.shopping.utils.HtmlElementUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import static com.dynamite.shopping.loader.HtmlElementLoader.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.util.List;

public class HtmlElementDecorator implements FieldDecorator {
    protected ElementLocatorFactory factory;
    
    public HtmlElementDecorator(CustomElementLocatorFactory factory) {
        this.factory = factory;
    }

    public Object decorate(ClassLoader loader, Field field) {
        try {
            if (HtmlElementUtils.isTypifiedElement(field)) {
                return decorateTypifiedElement(loader, field);
            }
            if (HtmlElementUtils.isHtmlElement(field)) {
                return decorateHtmlElement(loader, field);
            }
            if (HtmlElementUtils.isWebElement(field) && !field.getName().equals("wrappedElement")) {
                return decorateWebElement(loader, field);
            }
            if (HtmlElementUtils.isTypifiedElementList(field)) {
                return decorateTypifiedElementList(loader, field);
            }
            if (HtmlElementUtils.isHtmlElementList(field)) {
                return decorateHtmlElementList(loader, field);
            }
            if (HtmlElementUtils.isWebElementList(field)) {
                return decorateWebElementList(loader, field);
            }
            return null;
        } catch (ClassCastException ignore) {
            return null; // See bug #94 and NonElementFieldsTest
        }
    }

    protected <T extends TypifiedElement> T decorateTypifiedElement(ClassLoader loader, Field field) {
        WebElement elementToWrap = decorateWebElement(loader, field);

        //noinspection unchecked
        return createTypifiedElement((Class<T>) field.getType(), elementToWrap, HtmlElementUtils.getElementName(field));
    }

    protected <T extends HtmlElement> T decorateHtmlElement(ClassLoader loader, Field field) {
        WebElement elementToWrap = decorateWebElement(loader, field);

        //noinspection unchecked
        return createHtmlElement((Class<T>) field.getType(), elementToWrap, HtmlElementUtils.getElementName(field));
    }

    protected WebElement decorateWebElement(ClassLoader loader, Field field) {
        ElementLocator locator = factory.createLocator(field);
        InvocationHandler handler = new WebElementNamedProxyHandler(locator, HtmlElementUtils.getElementName(field));

        return ProxyFactory.createWebElementProxy(loader, handler);
    }

    protected <T extends TypifiedElement> List<T> decorateTypifiedElementList(ClassLoader loader, Field field) {
        @SuppressWarnings("unchecked")
        Class<T> elementClass = (Class<T>) HtmlElementUtils.getGenericParameterClass(field);
        ElementLocator locator = factory.createLocator(field);
        String name = HtmlElementUtils.getElementName(field);

        InvocationHandler handler = new TypifiedElementListNamedProxyHandler<>(elementClass, locator, name);

        return ProxyFactory.createTypifiedElementListProxy(loader, handler);
    }

    protected <T extends HtmlElement> List<T> decorateHtmlElementList(ClassLoader loader, Field field) {
        @SuppressWarnings("unchecked")
        Class<T> elementClass = (Class<T>) HtmlElementUtils.getGenericParameterClass(field);
        ElementLocator locator = factory.createLocator(field);
        String name = HtmlElementUtils.getElementName(field);

        InvocationHandler handler = new HtmlElementListNamedProxyHandler<>(elementClass, locator, name);

        return ProxyFactory.createHtmlElementListProxy(loader, handler);
    }

    protected List<WebElement> decorateWebElementList(ClassLoader loader, Field field) {
        ElementLocator locator = factory.createLocator(field);
        InvocationHandler handler = new WebElementListNamedProxyHandler(locator, HtmlElementUtils.getElementName(field));

        return ProxyFactory.createWebElementListProxy(loader, handler);
    }
}

package com.dynamite.shopping.loader;

import com.dynamite.shopping.element.HtmlElement;
import com.dynamite.shopping.element.TypifiedElement;
import com.dynamite.shopping.exceptions.HtmlElementsException;
import com.dynamite.shopping.loader.decorator.HtmlElementDecorator;
import com.dynamite.shopping.loader.decorator.HtmlElementLocatorFactory;
import com.dynamite.shopping.loader.decorator.ProxyFactory;
import com.dynamite.shopping.loader.decorator.proxyhandlers.WebElementNamedProxyHandler;
import com.dynamite.shopping.pagefactory.CustomElementLocatorFactory;
import com.dynamite.shopping.utils.HtmlElementUtils;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;

public class HtmlElementLoader {
   
    @SuppressWarnings("unchecked")
    public static <T> T create(Class<T> clazz, WebDriver driver) {
        if (HtmlElementUtils.isHtmlElement(clazz)) {
            return (T) createHtmlElement((Class<HtmlElement>) clazz, driver);
        }
        if (HtmlElementUtils.isTypifiedElement(clazz)) {
            return (T) createTypifiedElement((Class<TypifiedElement>) clazz, driver);
        }
        return createPageObject(clazz, driver);
    }

   
    public static <T> void populate(T instance, WebDriver driver) {
        if (HtmlElementUtils.isHtmlElement(instance)) {
            populateHtmlElement((HtmlElement) instance, driver);
        } else {
            // Otherwise consider instance as a page object
            populatePageObject(instance, driver);
        }
    }

   
    public static <T extends HtmlElement> T createHtmlElement(Class<T> clazz, SearchContext searchContext) {
        ElementLocator locator = new HtmlElementLocatorFactory(searchContext).createLocator(clazz);
        String elementName = HtmlElementUtils.getElementName(clazz);

        InvocationHandler handler = new WebElementNamedProxyHandler(locator, elementName);
        WebElement elementToWrap = ProxyFactory.createWebElementProxy(clazz.getClassLoader(), handler);
        return createHtmlElement(clazz, elementToWrap, elementName);
    }

    public static <T extends HtmlElement> T createHtmlElement(Class<T> elementClass, WebElement elementToWrap,
                                                              String name) {
        try {
            T instance = HtmlElementUtils.newInstance(elementClass);
            instance.setWrappedElement(elementToWrap);
            instance.setName(name);
            // Recursively initialize elements of the block
            populatePageObject(instance, elementToWrap);
            return instance;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException
                | InvocationTargetException e) {
            throw new HtmlElementsException(e);
        }
    }

    public static <T extends TypifiedElement> T createTypifiedElement(Class<T> clazz, SearchContext searchContext) {
        ElementLocator locator = new HtmlElementLocatorFactory(searchContext).createLocator(clazz);
        String elementName = HtmlElementUtils.getElementName(clazz);

        InvocationHandler handler = new WebElementNamedProxyHandler(locator, elementName);
        WebElement elementToWrap = ProxyFactory.createWebElementProxy(clazz.getClassLoader(), handler);

        return createTypifiedElement(clazz, elementToWrap, elementName);
    }

    public static <T extends TypifiedElement> T createTypifiedElement(Class<T> elementClass, WebElement elementToWrap,
                                                                      String name) {
        try {
            T instance = HtmlElementUtils.newInstance(elementClass, elementToWrap);
            instance.setName(name);
            return instance;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException
                | InvocationTargetException e) {
            throw new HtmlElementsException(e);
        }
    }

    
    public static <T> T createPageObject(Class<T> clazz, WebDriver driver) {
        try {
            T instance = HtmlElementUtils.newInstance(clazz, driver);
            populatePageObject(instance, driver);
            return instance;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException
                | InvocationTargetException e) {
            throw new HtmlElementsException(e);
        }
    }

   
    public static void populateHtmlElement(HtmlElement htmlElement, SearchContext searchContext) {
        populateHtmlElement(htmlElement, new HtmlElementLocatorFactory(searchContext));
    }

    
    public static void populateHtmlElement(HtmlElement htmlElement, CustomElementLocatorFactory locatorFactory) {
        @SuppressWarnings("unchecked")
        Class<HtmlElement> elementClass = (Class<HtmlElement>) htmlElement.getClass();
        // Create locator that will handle Block annotation
        ElementLocator locator = locatorFactory.createLocator(elementClass);
        ClassLoader elementClassLoader = htmlElement.getClass().getClassLoader();
        // Initialize block with WebElement proxy and set its name
        String elementName = HtmlElementUtils.getElementName(elementClass);
        InvocationHandler handler = new WebElementNamedProxyHandler(locator, elementName);
        WebElement elementToWrap = ProxyFactory.createWebElementProxy(elementClassLoader, handler);
        htmlElement.setWrappedElement(elementToWrap);
        htmlElement.setName(elementName);
        // Initialize elements of the block
        populatePageObject(htmlElement, elementToWrap);
    }   
    
    public static void populatePageObject(Object page, SearchContext searchContext) {
        populatePageObject(page, new HtmlElementLocatorFactory(searchContext));
    }
    
    public static void populatePageObject(Object page, CustomElementLocatorFactory locatorFactory) {
        PageFactory.initElements(new HtmlElementDecorator(locatorFactory), page);
    }
}

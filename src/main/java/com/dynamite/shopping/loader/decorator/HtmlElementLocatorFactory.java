package com.dynamite.shopping.loader.decorator;

import com.dynamite.shopping.annotations.FindByJQuery;
import com.dynamite.shopping.annotations.FindByJS;
import com.dynamite.shopping.annotations.Timeout;
import com.dynamite.shopping.pagefactory.CustomElementLocatorFactory;
import com.dynamite.shopping.utils.Driver.BrowserDriver;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.pagefactory.AjaxElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

public class HtmlElementLocatorFactory implements CustomElementLocatorFactory {
    private SearchContext searchContext;
   
    public HtmlElementLocatorFactory(SearchContext searchContext) {
        this.searchContext = searchContext;        
    }
    
    public ElementLocator createLocator(Field field) {
    	    	 
    	 if (field.isAnnotationPresent(FindByJQuery.class)) {
    		 BrowserDriver driver = (BrowserDriver)searchContext;
    		 return new JQueryElementLocator(driver.getWrappedDriver(), field);
    	 }
    	 
    	 if (field.isAnnotationPresent(FindByJS.class)) {
    		 BrowserDriver driver = (BrowserDriver)searchContext;    	 
    		 return new JSElementLocator(driver.getWrappedDriver(), field);
    	 }
    	     	      
         return new AjaxElementLocator(searchContext, getTimeOut(field), new HtmlElementFieldAnnotationsHandler(field));
    }

    public ElementLocator createLocator(Class clazz) {
        //noinspection unchecked
        return new AjaxElementLocator(searchContext, getTimeOut(clazz), new HtmlElementClassAnnotationsHandler(clazz));
    }

    public int getTimeOut(Field field) {
        if (field.isAnnotationPresent(Timeout.class)) {
            return field.getAnnotation(Timeout.class).value();
        }
        if (field.getGenericType() instanceof Class) {
            return getTimeOut((Class) field.getGenericType());
        }
        return getTimeOut((Class) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0]);
    }

    public int getTimeOut(Class clazz) {
        //noinspection EmptyCatchBlock
        try {
            Method method = Timeout.class.getMethod("value");
            do {
                if (clazz.isAnnotationPresent(Timeout.class)) {
                    return (Integer) method.invoke(clazz.getAnnotation(Timeout.class));
                }
                clazz = clazz.getSuperclass();
            } while (clazz != Object.class && clazz != null);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
        }

        return Integer.getInteger("webdriver.timeouts.implicitlywait", 5);
    }
}

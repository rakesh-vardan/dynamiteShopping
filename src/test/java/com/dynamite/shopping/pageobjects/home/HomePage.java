package com.dynamite.shopping.pageobjects.home;

import com.dynamite.shopping.element.*;
import com.dynamite.shopping.exceptions.HtmlElementsException;
import com.dynamite.shopping.loader.HtmlElementLoader;
import com.dynamite.shopping.page.Page;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class HomePage extends Page {

    private final String expectedUrl = "https://www.dynamiteclothing.xyz/ca";
    private final String expectedTitle = "metadata..title";

    private final String homePageError = "home page is not displayed";

    @FindBy(id = "loggedin-user-icon")
    private Image loggedInUserImage;

    @FindBy(id = "profile-myprofile")
    private Link myProfile;

    public HomePage(WebDriver driver) {
        super(driver);
        HtmlElementLoader.populatePageObject(this, driver);
    }

    public HomePage verifyPageDetails() {
        if (!urlContains(expectedUrl) || !titleContains(expectedTitle))
            throw new HtmlElementsException(homePageError);

        return this;
    }

    public String getTextOfUserProfile(WebDriver driver) throws InterruptedException {
        Thread.sleep(5);
        Actions actions = new Actions(driver);
        actions.moveToElement(loggedInUserImage).build().perform();
        Thread.sleep(2);
        actions.moveToElement(myProfile).perform();
        Thread.sleep(2);
        return myProfile.getText();
    }

}

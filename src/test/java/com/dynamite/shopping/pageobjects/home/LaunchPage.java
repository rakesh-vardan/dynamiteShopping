package com.dynamite.shopping.pageobjects.home;

import com.dynamite.shopping.element.Button;
import com.dynamite.shopping.element.HtmlElement;
import com.dynamite.shopping.exceptions.HtmlElementsException;
import com.dynamite.shopping.loader.HtmlElementLoader;
import com.dynamite.shopping.page.Page;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LaunchPage extends Page {

    private final String siteUrl = "https://www.dynamiteclothing.xyz/ca";
    private final String expectedUrl = "https://www.dynamiteclothing.xyz/ca";
    private final String expectedTitle = "Dynamite | Flaunt The Latest In Fashion - Free Shipping $40+";

    private final String homePageError = "launching page is not displayed";

    @FindBy(className = "welcomeLink")
    private HtmlElement welcomeLink;

    @FindBy(xpath = "//div[contains(text(),'Canada')]")
    private HtmlElement countryPicker;

    @FindBy(id = "cboxClose")
    private Button ad;

    public LaunchPage(WebDriver driver) {
        super(driver);
        HtmlElementLoader.populatePageObject(this, driver);
    }

    public LaunchPage open() {
        getDriver().get(siteUrl);
        closePopUps();

        if (!urlContains(expectedUrl) || !titleContains(expectedTitle))
            throw new HtmlElementsException(homePageError);
        return this;
    }

    public LoginPage clickWelcomeLink() {
        LoginPage loginPage = new LoginPage(getDriver());
        welcomeLink.click();
        return loginPage;
    }

    private void closePopUps() {

        boolean isPopupPresent = false;
        WebDriverWait wait = new WebDriverWait(getDriver(), 5);
        HtmlElement selectCountryPopUp = null;
        try {
            selectCountryPopUp = (HtmlElement) wait.until(ExpectedConditions.visibilityOf(countryPicker));
        } catch (TimeoutException e1) {
            closeAdvertisementPopUp(wait);
        }

        if (selectCountryPopUp != null && selectCountryPopUp.exists()) {
            countryPicker.click();
            isPopupPresent = true;
        }

        if (!isPopupPresent) {
            closeAdvertisementPopUp(wait);
        }
    }

    private void closeAdvertisementPopUp(WebDriverWait wait) {
        Button closeButtonOnAdvertisement = (Button) wait.until(ExpectedConditions.visibilityOf(ad));
        if (closeButtonOnAdvertisement.exists()) {
            ad.click();
        }
    }
}
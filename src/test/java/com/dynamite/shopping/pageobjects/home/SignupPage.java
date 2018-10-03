package com.dynamite.shopping.pageobjects.home;

import com.dynamite.shopping.annotations.Name;
import com.dynamite.shopping.element.HtmlElement;
import com.dynamite.shopping.exceptions.HtmlElementsException;
import com.dynamite.shopping.loader.HtmlElementLoader;
import com.dynamite.shopping.page.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class SignupPage extends Page {

    private final String expectedUrl = "https://www.dynamiteclothing.xyz/ca/profile/login?h=true";
    private final String expectedTitle = "Dynamite | Flaunt The Latest In Fashion - Free Shipping $40+";

    private final String signupPageError = "login page is not displayed";

    @Name("CREATE_MY_ACCOUNT")
    @FindBy(linkText = "CREATE MY ACCOUNT")
    private HtmlElement createMyAccount;

    public SignupPage(WebDriver driver) {
        super(driver);
        HtmlElementLoader.populatePageObject(this, driver);
    }

    public SignupPage verifyPageDetails() {
        if (!urlContains(expectedUrl) || !titleContains(expectedTitle))
            throw new HtmlElementsException(signupPageError);

        return this;
    }

    public void verifyText() {
        if (!createMyAccount.getText().equals("CREATE MY ACCOUNT"))
            throw new HtmlElementsException(signupPageError);
    }
}

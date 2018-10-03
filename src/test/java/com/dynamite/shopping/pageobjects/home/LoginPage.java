package com.dynamite.shopping.pageobjects.home;

import com.dynamite.shopping.element.Button;
import com.dynamite.shopping.element.HtmlElement;
import com.dynamite.shopping.element.TextInput;
import com.dynamite.shopping.exceptions.HtmlElementsException;
import com.dynamite.shopping.loader.HtmlElementLoader;
import com.dynamite.shopping.page.Page;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends Page {

    private final String expectedUrl = "https://www.dynamiteclothing.xyz/ca/profile/login?h=true";
    private final String expectedTitle = "Dynamite | Flaunt the Latest in Fashion - Free Shipping $40+";

    private final String loginPageError = "login page is not displayed";

    @FindBy(linkText = "CREATE MY ACCOUNT")
    private HtmlElement createMyAccount;

    @FindBy(id = "email_input")
    private TextInput email;

    @FindBy(id = "password_input")
    private TextInput password;

    @FindBy(id = "login-button")
    private Button loginButton;

    public LoginPage(WebDriver driver) {
        super(driver);
        HtmlElementLoader.populatePageObject(this, driver);
    }

    public LoginPage verifyPageDetails() {
        if (!urlContains(expectedUrl) || !titleContains(expectedTitle))
            throw new HtmlElementsException(loginPageError);

        return this;
    }

    public void verifyCreateMyAccountText() {
        if (!createMyAccount.getText().equals("CREATE MY ACCOUNT"))
            throw new HtmlElementsException(loginPageError);
    }

    public SignupPage openSignUpPage() {
        createMyAccount.click();
        SignupPage signUpPage = new SignupPage(getDriver());
        return signUpPage;
    }

    public HomePage login(String emailAddress, String pwd) {
        enterUserEmail(emailAddress);
        enterPassword(pwd);
        clickLogin();
        return new HomePage(getDriver());
    }

    private void enterUserEmail(String emailAddress) {
        if (StringUtils.isNotEmpty(email.getText())) {
            email.clear();
        }
        email.sendKeys(emailAddress);
    }

    private void enterPassword(String pwd) {
        if (StringUtils.isNotEmpty(password.getText())) {
            password.clear();
        }
        password.sendKeys(pwd);
    }

    private void clickLogin() {
        loginButton.click();
    }

}

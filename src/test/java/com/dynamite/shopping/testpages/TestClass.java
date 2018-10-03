package com.dynamite.shopping.testpages;

import com.dynamite.shopping.BaseTest.BaseTestClass;
import com.dynamite.shopping.pageobjects.home.HomePage;
import com.dynamite.shopping.pageobjects.home.LaunchPage;
import com.dynamite.shopping.pageobjects.home.LoginPage;
import com.dynamite.shopping.testlisteners.TestListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestListener.class)
public class TestClass extends BaseTestClass {

    @Test
    public void openHomePage() {
        LaunchPage launchPage = new LaunchPage(driver);
        launchPage.open();
        LoginPage loginPage = launchPage.clickWelcomeLink();
        loginPage.verifyPageDetails();
        loginPage.verifyCreateMyAccountText();
        HomePage homePage = loginPage.login("rakesh.budugu@yahoo.com", "Dynamite#1");
        homePage.verifyPageDetails();
//        assertEquals(loginPage.getTextOfUserProfile(driver),"MY PROFILE");
    }

}

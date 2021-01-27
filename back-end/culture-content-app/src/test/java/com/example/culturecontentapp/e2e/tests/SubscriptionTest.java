package com.example.culturecontentapp.e2e.tests;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static com.example.culturecontentapp.e2e.constants.SharedConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.concurrent.TimeUnit;

import com.example.culturecontentapp.e2e.pages.HomePage;
import com.example.culturecontentapp.e2e.pages.LoginPage;
import com.example.culturecontentapp.e2e.pages.SubscriptionPage;

public class SubscriptionTest {
    private WebDriver driver;
    private HomePage homePage;
    private LoginPage loginPage;
    private SubscriptionPage subscriptionPage;

    @Before
    public void setup() {
        System.setProperty(WEB_DRIVER, WEB_DRIVER_PATH);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.navigate().to(LOGIN_PATH);

        loginPage = PageFactory.initElements(driver, LoginPage.class);
    }

    public void login() {
        loginPage.ensureIsDisplayed();
        loginPage.getUsernameInput().sendKeys(USER_EMAIL);
        loginPage.getPasswordInput().sendKeys(USER_PASS);
        loginPage.getLoginButton().click();
    }

    private void openSubscriptions() {
        login();
        sleep(2000);
        homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.ensureMySubscriptionsButtonIsDisplayed();
        homePage.getMySubscriptionsButton().click();
        subscriptionPage = PageFactory.initElements(driver, SubscriptionPage.class);
    }

    private void sleep(int milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void pagination_shouldReturnSuccess() {
        openSubscriptions();
        String firstNewsText = subscriptionPage.getSubText().getText();
        subscriptionPage.ensureNextPaginationButtonIsDisplayed();
        subscriptionPage.getNextPaginationButton().click();
        sleep(500);
        assertNotEquals(firstNewsText, subscriptionPage.getSubText().getText());

        subscriptionPage.getPreviousPaginationButton().click();
        sleep(500);
        assertEquals(firstNewsText, subscriptionPage.getSubText().getText());
    }
}

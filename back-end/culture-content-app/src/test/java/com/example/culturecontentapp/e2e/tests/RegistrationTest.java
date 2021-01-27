package com.example.culturecontentapp.e2e.tests;

import com.example.culturecontentapp.e2e.pages.RegistrationPage;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static com.example.culturecontentapp.e2e.constants.SharedConstants.*;
import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

public class RegistrationTest {

    private WebDriver browser;

    RegistrationPage registrationPage;

    @Before
    public void setup() {
        System.setProperty(WEB_DRIVER, WEB_DRIVER_PATH);
        browser = new ChromeDriver();
        browser.manage().window().maximize();
        browser.navigate().to(REGISTRATION_PATH);

        registrationPage = PageFactory.initElements(browser, RegistrationPage.class);
    }

    public void sleep(int milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void register_validData_shouldReturnSuccess() {
        registrationPage.getEmailInput().sendKeys(EXAMPLE_EMAIL);
        registrationPage.getUsernameInput().sendKeys(EXAMPLE_USERNAME);
        registrationPage.getPasswordInput().sendKeys(EXAMPLE_PASSWORD);
        registrationPage.getRepeatInput().sendKeys(EXAMPLE_PASSWORD);
        registrationPage.ensureSubmitButtonIsDisplayed();
        registrationPage.getRegistrationButton().click();
        sleep(3000);
        registrationPage.ensureSnackbarIsDisplayed();
        assertEquals("Confirmation mail has been sent. Please activate your account",
                registrationPage.getSnackMessage().getText());
    }

    @Test
    public void register_givenUsernameExists_shouldReturnError() {
        registrationPage.getEmailInput().sendKeys(EXAMPLE_EMAIL2);
        registrationPage.getUsernameInput().sendKeys(ADMIN_USERNAME);
        registrationPage.getPasswordInput().sendKeys(EXAMPLE_PASSWORD);
        registrationPage.getRepeatInput().sendKeys(EXAMPLE_PASSWORD);
        registrationPage.ensureSubmitButtonIsDisplayed();
        registrationPage.getRegistrationButton().click();
        sleep(3000);
        registrationPage.ensureSnackbarIsDisplayed();
        assertEquals("Account with the given username already exists", registrationPage.getSnackMessage().getText());
    }

    @Test
    public void register_givenEmailExists_shouldReturnError() {
        registrationPage.getEmailInput().sendKeys(ADMIN_EMAIL);
        registrationPage.getUsernameInput().sendKeys(EXAMPLE_USERNAME2);
        registrationPage.getPasswordInput().sendKeys(EXAMPLE_PASSWORD);
        registrationPage.getRepeatInput().sendKeys(EXAMPLE_PASSWORD);
        registrationPage.ensureSubmitButtonIsDisplayed();
        registrationPage.getRegistrationButton().click();
        sleep(3000);
        registrationPage.ensureSnackbarIsDisplayed();
        assertEquals("Account with the given email already exists", registrationPage.getSnackMessage().getText());
    }
}

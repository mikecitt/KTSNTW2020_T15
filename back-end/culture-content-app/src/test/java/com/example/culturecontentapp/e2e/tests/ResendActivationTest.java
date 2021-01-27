package com.example.culturecontentapp.e2e.tests;

import static com.example.culturecontentapp.e2e.constants.SharedConstants.*;
import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import com.example.culturecontentapp.e2e.pages.RegistrationPage;
import com.example.culturecontentapp.e2e.pages.ResendActivationPage;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

public class ResendActivationTest {

    private WebDriver browser;

    ResendActivationPage resendActivationPage;

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

    public void register() {
        registrationPage.getEmailInput().sendKeys(EXAMPLE_EMAIL);
        registrationPage.getUsernameInput().sendKeys(EXAMPLE_USERNAME);
        registrationPage.getPasswordInput().sendKeys(EXAMPLE_PASSWORD);
        registrationPage.getRepeatInput().sendKeys(EXAMPLE_PASSWORD);
        registrationPage.ensureSubmitButtonIsDisplayed();
        registrationPage.getRegistrationButton().click();
        sleep(3000);
        registrationPage.ensureSnackbarIsDisplayed();
    }

    @Test
    public void resendActivation_validData_shouldReturnSuccess() {
        register();
        browser.navigate().to(RESEND_ACTIVATION_PATH);
        resendActivationPage = PageFactory.initElements(browser, ResendActivationPage.class);
        resendActivationPage.getEmailInput().sendKeys(EXAMPLE_EMAIL);
        resendActivationPage.ensureSubmitButtonIsDisplayed();
        resendActivationPage.getSubmitButton().click();
        sleep(500);
        resendActivationPage.ensureSnackbarIsDisplayed();
        assertEquals("Confirmation mail has been sent. Please activate your account",
                registrationPage.getSnackMessage().getText());
    }

    @Test
    public void resendActivation_givenEmailDoesntExist_shouldReturnError() {
        browser.navigate().to(RESEND_ACTIVATION_PATH);
        resendActivationPage = PageFactory.initElements(browser, ResendActivationPage.class);
        resendActivationPage.getEmailInput().sendKeys(EXAMPLE_EMAIL + "aa");
        resendActivationPage.ensureSubmitButtonIsDisplayed();
        resendActivationPage.getSubmitButton().click();
        sleep(500);
        resendActivationPage.ensureSnackbarIsDisplayed();
        assertEquals("Account with the given email doesn't exists", registrationPage.getSnackMessage().getText());
    }

    @Test
    public void resendActivation_givenEmailAlreadyActive_shouldReturnError() {
        browser.navigate().to(RESEND_ACTIVATION_PATH);
        resendActivationPage = PageFactory.initElements(browser, ResendActivationPage.class);
        resendActivationPage.getEmailInput().sendKeys(ADMIN_EMAIL);
        resendActivationPage.ensureSubmitButtonIsDisplayed();
        resendActivationPage.getSubmitButton().click();
        sleep(500);
        resendActivationPage.ensureSnackbarIsDisplayed();
        assertEquals("Account with the given email is already active", registrationPage.getSnackMessage().getText());
    }
}

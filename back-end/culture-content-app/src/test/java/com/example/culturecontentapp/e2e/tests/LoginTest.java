package com.example.culturecontentapp.e2e.tests;

import com.example.culturecontentapp.e2e.pages.LoginPage;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static com.example.culturecontentapp.e2e.constants.SharedConstants.*;
import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

public class LoginTest {

  private WebDriver browser;

  LoginPage loginPage;

  @Before
  public void setup() {
    System.setProperty(WEB_DRIVER, WEB_DRIVER_PATH);
    browser = new ChromeDriver();
    browser.manage().window().maximize();
    browser.navigate().to(LOGIN_PATH);

    loginPage = PageFactory.initElements(browser, LoginPage.class);
  }

  public void sleep(int milliseconds) {
    try {
      TimeUnit.MILLISECONDS.sleep(milliseconds);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void login_SuccessfullyLogins() {
    loginPage.getUsernameInput().sendKeys(USER_EMAIL);
    loginPage.getPasswordInput().sendKeys(USER_PASS);
    loginPage.getLoginButton().click();
    sleep(3000);
    assertEquals("http://localhost:4200/", browser.getCurrentUrl());
  }

  @Test
  public void login_ThrowsNotFound() {
    loginPage.getUsernameInput().sendKeys(EXAMPLE_EMAIL);
    loginPage.getPasswordInput().sendKeys(USER_PASS);
    loginPage.getLoginButton().click();
    sleep(3000);
    loginPage.ensureSnackbarIsDisplayed();
    assertEquals("Account with the given username not found", loginPage.getSnackMessage().getText());
  }

  @Test
  public void login_ThrowsPasswordIncorect() {
    loginPage.getUsernameInput().sendKeys(ADMIN_EMAIL);
    loginPage.getPasswordInput().sendKeys(EXAMPLE_PASSWORD);
    loginPage.getLoginButton().click();
    sleep(3000);
    loginPage.ensureSnackbarIsDisplayed();
    assertEquals("Password is incorrect", loginPage.getSnackMessage().getText());
  }
}

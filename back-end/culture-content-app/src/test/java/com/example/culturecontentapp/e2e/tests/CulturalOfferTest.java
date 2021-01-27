package com.example.culturecontentapp.e2e.tests;

import java.nio.file.FileSystems;
import java.util.concurrent.TimeUnit;

import com.example.culturecontentapp.e2e.pages.HomePage;
import com.example.culturecontentapp.e2e.pages.LoginPage;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static com.example.culturecontentapp.e2e.constants.SharedConstants.*;
import static org.junit.Assert.assertEquals;

public class CulturalOfferTest {

  private WebDriver browser;

  HomePage homePage;
  LoginPage loginPage;

  @Before
  public void setup() {
    System.setProperty(WEB_DRIVER, WEB_DRIVER_PATH);
    browser = new ChromeDriver();
    browser.manage().window().maximize();
    browser.navigate().to(LOGIN_PATH);

    loginPage = PageFactory.initElements(browser, LoginPage.class);
  }

  public void testLogin() {
    loginPage.ensureIsDisplayed();
    loginPage.getUsernameInput().sendKeys(ADMIN_EMAIL);
    loginPage.getPasswordInput().sendKeys(ADMIN_PASS);
    loginPage.getLoginButton().click();
  }

  public void sleep(int milliseconds) {
    try {
      TimeUnit.MILLISECONDS.sleep(milliseconds);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void test1_login_SuccessfullyLogins() {
    testLogin();

    sleep(3000);
    homePage = PageFactory.initElements(browser, HomePage.class);
    homePage.getAddCulturalOfferButton().click();

    homePage.initalizeOfferSelectElements();

    homePage.getNameInput().sendKeys(CULTURAL_OFFER_NAME);
    homePage.getDescriptionInput().sendKeys(CULTURAL_OFFER_DESCRIPTION);
    homePage.getLocationInput().sendKeys(CULTURAL_OFFER_LOCATION);
    sleep(500);
    homePage.getLocationOption().click();

    homePage.getTypeOption().click();
    homePage.getTypeOption().chooseOption(1);

    homePage.getSubTypeOption().click();
    homePage.getSubTypeOption().chooseOption(1);

//    String absolutePath = FileSystems.getDefault().getPath("src/test/resources/wireframePrimer.PNG")
//            .normalize().toAbsolutePath().toString();
//
//    homePage.getOfferPicture().sendKeys(absolutePath);

    homePage.getSubmitOffer().click();
    homePage.ensureMarkerCount(5);
  }

  @Test
  public void test2_createOffer_AlreadyExist() {
    testLogin();

    sleep(3000);
    homePage = PageFactory.initElements(browser, HomePage.class);
    homePage.getAddCulturalOfferButton().click();

    homePage.initalizeOfferSelectElements();

    homePage.getNameInput().sendKeys(CULTURAL_OFFER_NAME);
    homePage.getDescriptionInput().sendKeys(CULTURAL_OFFER_DESCRIPTION);
    homePage.getLocationInput().sendKeys(CULTURAL_OFFER_LOCATION);
    sleep(500);
    homePage.getLocationOption().click();

    homePage.getTypeOption().click();
    homePage.getTypeOption().chooseOption(1);

    homePage.getSubTypeOption().click();
    homePage.getSubTypeOption().chooseOption(1);

    homePage.getSubmitOffer().click();

    assertEquals("Cultural offer with the given name already exists",homePage.getSnackMessage().getText());
  }

  @Test
  public void test3_edit_SuccessfullyLogins() {
    testLogin();

    sleep(3000);
    homePage = PageFactory.initElements(browser, HomePage.class);
    homePage.getMapMarker().click();
    sleep(500);
    homePage.getEditOfferButton().click();


    homePage.getEditOfferName().clear();
    homePage.getEditOfferName().sendKeys("Nova ponudaaaa");
    homePage.getEditDescription().clear();
    homePage.getEditDescription().sendKeys(CULTURAL_OFFER_DESCRIPTION);
    homePage.getEditLocation().clear();
    homePage.getEditLocation().sendKeys(CULTURAL_OFFER_LOCATION);
    sleep(500);
    homePage.getEditLocationOption().click();

    homePage.getSubmitEdit().click();
    sleep(800);

    assertEquals("Updated successully",homePage.getSnackMessage().getText());

  }

}

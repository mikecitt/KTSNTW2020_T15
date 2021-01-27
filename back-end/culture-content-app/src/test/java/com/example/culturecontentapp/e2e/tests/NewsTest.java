package com.example.culturecontentapp.e2e.tests;

import com.example.culturecontentapp.e2e.pages.HomePage;
import com.example.culturecontentapp.e2e.pages.LoginPage;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static com.example.culturecontentapp.e2e.constants.SharedConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.concurrent.TimeUnit;

public class NewsTest {
    private WebDriver driver;
    private HomePage homePage;
    private LoginPage loginPage;

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
        loginPage.getUsernameInput().sendKeys(ADMIN_EMAIL);
        loginPage.getPasswordInput().sendKeys(ADMIN_PASS);
        loginPage.getLoginButton().click();
    }

    private void openNews(){
        login();
        sleep(2000);
        homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.ensureIsDisplayed();
        homePage.getMapMarker().click();

        homePage.ensureTabLabelNewsIsDisplayed();
        homePage.getTabLabelNews().click();

        homePage.ensureAddNewsButtonIsDisplayed();
        
    }

    private void sleep(int milliseconds){
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void create_validParams_willReturnSucceed() {
        openNews();
        homePage.getAddNewsButton().click();

        homePage.ensureTextInputNewsIsDisplayed();
        homePage.getTextInputNews().sendKeys("Dovoljno je da ima deset slova");

        homePage.getAddNewsFormButton().click();
        sleep(500);
        homePage.ensureSnackbarIsDisplayed();
        assertEquals("News successfully added", homePage.getSnackMessage().getText());

    }

    @Test
    public void create_noText_willReturnFail() {
        openNews();
        homePage.getAddNewsButton().click();

        homePage.ensureTextInputNewsIsDisplayed();

        homePage.getAddNewsFormButton().click();
        sleep(500);
        homePage.ensureSnackbarIsDisplayed();
        assertEquals("Description must be between 10 and 256 characters", homePage.getSnackMessage().getText());

    }

    @Test
    public void create_notEnoughText_willReturnFail() {
        openNews();
        homePage.getAddNewsButton().click();

        homePage.ensureTextInputNewsIsDisplayed();
        homePage.getTextInputNews().sendKeys("Nema do.");

        homePage.getAddNewsFormButton().click();
        sleep(500);
        homePage.ensureSnackbarIsDisplayed();
        assertEquals("Description must be between 10 and 256 characters", homePage.getSnackMessage().getText());

    }

    @Test
    public void delete_shouldReturnSuccess() {
        openNews();
        String newsToDelete = homePage.getNewsText().getText();
        homePage.ensureDeleteNewsFormButtonIsDisplayed();
        homePage.getDeleteNewsFormButton().click();
        homePage.ensureYesDeleteButtonIsDisplayed();
        homePage.getYesDeleteButton().click();
        sleep(500);
        homePage.ensureSnackbarIsDisplayed();
        assertEquals("News deleted successfully", homePage.getSnackMessage().getText());
        assertNotEquals(newsToDelete, homePage.getNewsText().getText());
    }

    @Test
    public void update_validText_shouldReturnSuccess() {
        openNews();
        String newsToUpdate = homePage.getNewsText().getText();
        homePage.getUpdateNewsButton().click();
        homePage.getTextInputNews().clear();
        homePage.getTextInputNews().sendKeys("Testiranje update vesti");
        homePage.getUpdateNewsFormButton().click();
        sleep(500);
        homePage.ensureSnackbarIsDisplayed();
        assertEquals("News successfully updated", homePage.getSnackMessage().getText());
        assertNotEquals(newsToUpdate, homePage.getNewsText().getText());

    }

    @Test
    public void update_invalidText_shouldReturnSuccess() {
        openNews();
        String newsToUpdate = homePage.getNewsText().getText();
        homePage.getUpdateNewsButton().click();
        homePage.getTextInputNews().clear();
        homePage.getTextInputNews().sendKeys("Nema do.");
        homePage.getUpdateNewsFormButton().click();
        sleep(500);
        homePage.ensureSnackbarIsDisplayed();
        assertEquals("Description must be between 10 and 256 characters", homePage.getSnackMessage().getText());
        assertEquals(newsToUpdate, homePage.getNewsText().getText());
    }

    @Test
    public void pagination_shouldReturnSuccess(){
        openNews();
        String firstNewsText = homePage.getNewsText().getText();
        homePage.ensureNextPaginationButtonIsDisplayed();
        homePage.getNextPaginationButton().click();
        sleep(500);
        assertNotEquals(firstNewsText, homePage.getNewsText().getText());

        homePage.getPreviousPaginationButton().click();
        sleep(500);
        assertEquals(firstNewsText, homePage.getNewsText().getText());

    }
    
    
}

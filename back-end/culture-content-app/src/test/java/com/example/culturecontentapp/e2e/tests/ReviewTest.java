package com.example.culturecontentapp.e2e.tests;

import com.example.culturecontentapp.e2e.pages.HomePage;
import com.example.culturecontentapp.e2e.pages.LoginPage;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import static com.example.culturecontentapp.e2e.constants.SharedConstants.*;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.concurrent.TimeUnit;

public class ReviewTest {
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
        loginPage.getUsernameInput().sendKeys(USER_EMAIL);
        loginPage.getPasswordInput().sendKeys(USER_PASS);
        loginPage.getLoginButton().click();
    }

    private void openReviews() {
        login();
        sleep(2000);
        homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.ensureIsDisplayed();
        homePage.getMapMarker().click();

        homePage.ensureTabLabelReviewsIsDisplayed();
        homePage.getTabLabelReviews().click();

        homePage.ensureAddNewsButtonIsDisplayed();
    }

    private void sleep(int milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void create_validParams_willReturnSucceed() {
        openReviews();
        homePage.getAddNewsButton().click();
        sleep(500);
        driver.findElement(By.cssSelector("#mat-input-3")).sendKeys("Dovoljno je da ima deset slova");

        driver.findElement(By.cssSelector("#formRating")).findElement(By.cssSelector("#star_3")).click();

        driver.findElement(By.cssSelector("#add-reviews-form-button")).click();

        homePage.ensureSnackbarIsDisplayed();
        assertEquals("Review successfuly added", homePage.getSnackMessage().getText());
    }

    @Test
    public void create_notEnoughtText_willReturnError() {
        openReviews();
        homePage.getAddNewsButton().click();
        sleep(500);

        driver.findElement(By.cssSelector("#mat-input-3")).sendKeys("krtk");

        driver.findElement(By.cssSelector("#formRating")).findElement(By.cssSelector("#star_3")).click();

        assertFalse(driver.findElement(By.cssSelector("#add-reviews-form-button")).isEnabled());
    }

    @Test
    public void create_noStarRating_willReturnError() {
        openReviews();
        homePage.getAddNewsButton().click();
        sleep(500);

        driver.findElement(By.cssSelector("#mat-input-3")).sendKeys("krtk");

        assertFalse(driver.findElement(By.cssSelector("#add-reviews-form-button")).isEnabled());
    }

    @Test
    public void pagination_shouldReturnSuccess() {
        openReviews();
        String firstReviewText = homePage.getReviewText().getText();
        homePage.ensureNextPaginationButtonIsDisplayed();
        homePage.getNextPaginationButton().click();
        sleep(500);
        assertNotEquals(firstReviewText, homePage.getReviewText().getText());

        homePage.getPreviousPaginationButton().click();
        sleep(500);
        assertEquals(firstReviewText, homePage.getReviewText().getText());
    }

}

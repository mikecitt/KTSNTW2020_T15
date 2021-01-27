package com.example.culturecontentapp.e2e.pages;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
@RequiredArgsConstructor
public class RegistrationPage {

    @Getter(value = AccessLevel.PRIVATE)
    private final WebDriver driver;

    @FindBy(css = "#registration-username-input")
    private WebElement usernameInput;

    @FindBy(css = "#registration-email-input")
    private WebElement emailInput;

    @FindBy(css = "#registration-password-input")
    private WebElement passwordInput;

    @FindBy(css = "#registration-repeat-input")
    private WebElement repeatInput;

    @FindBy(css = "#registration-submit-button")
    private WebElement registrationButton;

    @FindBy(css = ".mat-simple-snackbar")
    private WebElement snackMessage;

    public void ensureSubmitButtonIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(registrationButton));
    }

    public void ensureSnackbarIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(snackMessage));
    }
}

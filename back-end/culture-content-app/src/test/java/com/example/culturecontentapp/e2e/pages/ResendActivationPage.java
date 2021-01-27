package com.example.culturecontentapp.e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResendActivationPage {
    @Getter(value = AccessLevel.PRIVATE)
    private final WebDriver driver;

    @FindBy(css = "#resend-email-input")
    private WebElement emailInput;

    @FindBy(css = "#resend-submit-button")
    private WebElement submitButton;

    @FindBy(css = ".mat-simple-snackbar")
    private WebElement snackMessage;

    public void ensureSubmitButtonIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(submitButton));
    }

    public void ensureSnackbarIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(snackMessage));
    }
}

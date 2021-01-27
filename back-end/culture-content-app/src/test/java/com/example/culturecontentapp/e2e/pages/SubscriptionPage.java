package com.example.culturecontentapp.e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import lombok.Getter;

@Getter
public class SubscriptionPage {

    private WebDriver driver;

    public SubscriptionPage(WebDriver driver) {
        this.driver = driver;
    }

    @FindBy(id = "sub-text")
    private WebElement subText;

    @FindBy(id = "pagination-button-next")
    private WebElement nextPaginationButton;

    @FindBy(id = "pagination-button-previous")
    private WebElement previousPaginationButton;

    public void ensureNextPaginationButtonIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(nextPaginationButton));
    }

    public void ensurePreviousPaginationButtonIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(previousPaginationButton));
    }
}

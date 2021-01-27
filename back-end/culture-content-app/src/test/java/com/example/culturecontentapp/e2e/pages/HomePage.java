package com.example.culturecontentapp.e2e.pages;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class HomePage {

    private WebDriver driver;
    private final SelectWebElement typeSelect;
    private SelectWebElement subTypeSelect;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.typeSelect = new SelectWebElement(driver, "#type-select");
        this.subTypeSelect = new SelectWebElement(driver, "#subtype-select");
    }

    @FindBy(id = "search-input")
    private WebElement searchInput;

    @FindBy(id = "apply-btn")
    private WebElement applyFilterButton;

    @FindBy(id = "reset-btn")
    private WebElement resetFilterButton;

    @FindBy(css = ".mapboxgl-marker")
    private WebElement mapMarker;

    @FindBy(css = ".add-button")
    private WebElement addNewsButton;

    @FindBy(css = "#mat-input-3")
    private WebElement textInputNews;

    @FindBy(css = "#mat-tab-label-0-1")
    private WebElement tabLabelNews;

    @FindBy(id = "add-news-button")
    private WebElement addNewsFormButton;

    @FindBy(id = "delete-news-button")
    private WebElement deleteNewsFormButton;

    @FindBy(id = "update-news-form-button")
    private WebElement updateNewsFormButton;

    @FindBy(id = "update-news-button")
    private WebElement updateNewsButton;

    @FindBy(css = ".mat-simple-snackbar")
    private WebElement snackMessage;

    @FindBy(css = "#confirm-delete-button")
    private WebElement yesDeleteButton;

    @FindBy(id = "news-text")
    private WebElement newsText;

    @FindBy(id = "nav-subscriptions")
    private WebElement mySubscriptionsButton;

    @FindBy(id = "pagination-button-next")
    private WebElement nextPaginationButton;

    @FindBy(id = "pagination-button-previous")
    private WebElement previousPaginationButton;

    public void ensureIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(applyFilterButton));
    }

    public void ensureMapMarkerIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(mapMarker));
    }

    public void ensureTabLabelNewsIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(tabLabelNews));
    }

    public void ensureAddNewsButtonIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(addNewsButton));
    }

    public void ensureTextInputNewsIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(textInputNews));
    }

    public void ensureSnackbarIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(snackMessage));
    }

    public void ensureDeleteNewsFormButtonIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(deleteNewsFormButton));
    }

    public void ensureUpdateNewsFormButtonIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(updateNewsFormButton));
    }

    public void ensureYesDeleteButtonIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(yesDeleteButton));
    }

    public void ensureNextPaginationButtonIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(nextPaginationButton));
    }

    public void ensureMySubscriptionsButtonIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(mySubscriptionsButton));
    }
}

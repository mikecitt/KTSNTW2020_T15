package com.example.culturecontentapp.e2e.pages;

import lombok.Getter;
import org.openqa.selenium.By;
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

    private SelectWebElement typeOption;
    private SelectWebElement subTypeOption;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.typeSelect = new SelectWebElement(driver, "#type-select");
        this.subTypeSelect = new SelectWebElement(driver, "#subtype-select");

    }

    public void initalizeOfferSelectElements() {
        this.typeOption = new SelectWebElement(driver, "#new-co-type-option");
        this.subTypeOption = new SelectWebElement(driver, "#new-co-subType-option");
    }

    @FindBy(id = "edit-offer-name")
    private WebElement editOfferName;

    @FindBy(css = "#edit-offer-description")
    private WebElement editDescription;

    @FindBy(id = "edit-offer-btn")
    private WebElement editOfferButton;

    @FindBy(id = "delete-offer-btn")
    private WebElement deleteOfferButton;

    @FindBy(id = "offer-pict")
    private WebElement offerPicture;

    @FindBy(id = "submitOffer")
    private WebElement submitOffer;

    @FindBy(id = "submit-edit")
    private WebElement submitEdit;

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

    @FindBy(css = "#new-co-name")
    private WebElement nameInput;

    @FindBy(css = "#new-co-description")
    private WebElement descriptionInput;

    @FindBy(css = "#new-co-location")
    private WebElement locationInput;

    @FindBy(css = "#edit-location")
    private WebElement editLocation;

    @FindBy(css = "#edit-co-location-option")
    private WebElement editLocationOption;

    @FindBy(css = "#new-co-location-option")
    private WebElement locationOption;

    @FindBy(css = ".newButton")
    private WebElement addCulturalOfferButton;

    @FindBy(css = "#mat-input-3")
    private WebElement textInputNews;

    @FindBy(css = "#mat-tab-label-0-1")
    private WebElement tabLabelNews;

    @FindBy(css = "#mat-tab-label-0-2")
    private WebElement tabLabelReviews;

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

    @FindBy(id = "review-text")
    private WebElement reviewText;

    @FindBy(id = "nav-subscriptions")
    private WebElement mySubscriptionsButton;

    @FindBy(id = "pagination-button-next")
    private WebElement nextPaginationButton;

    @FindBy(id = "pagination-button-previous")
    private WebElement previousPaginationButton;

    public void ensureIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(applyFilterButton));
    }

    public void ensureMarkerCount(int count) {
        String selector = String.format(".mapboxgl-marker");
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.numberOfElementsToBe(By.cssSelector(selector), count));
    }

    public void ensureMapMarkerIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(mapMarker));
    }

    public void ensureTabLabelNewsIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(tabLabelNews));
    }

    public void ensureTabLabelReviewsIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(tabLabelReviews));
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

    public void ensureCulturalOfferDeleteButtonDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(deleteOfferButton));
    }
}

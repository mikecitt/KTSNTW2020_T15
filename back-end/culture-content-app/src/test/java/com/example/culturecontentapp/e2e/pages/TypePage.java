package com.example.culturecontentapp.e2e.pages;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
@Setter
public class TypePage {

    @Getter(value= AccessLevel.PRIVATE)
    private WebDriver driver;

    @FindBy(id = "create_type_button")
    private WebElement addTypeButton;

    @FindBy(css = ".mat-table tbody .type-row:first-child .mat-column-options .mat-warn")
    private WebElement deleteTypeButton;

    @FindBy(css = ".mat-table tbody .type-row:nth-child(2) .mat-column-options .update-type-button")
   // @FindBy(id = "update_type_button")
    private WebElement updateTypeButton;

    @FindBy(id = "close_type_button")
    private WebElement closeTypeButton;

    @FindBy(id = "save_type_button")
    private WebElement saveTypeButton;

    @FindBy(css = "#confirm-delete-button")
    private WebElement yesTypeButton;

    @FindBy(xpath = "//mat-dialog-container[@id='mat-dialog-0']/app-confirm-delete/mat-dialog-actions/button[2]")
    private WebElement noTypeButton;

    @FindBy(id = "type_name")
    private WebElement typeNameInput;

    // ===== SUBTYPE TABLE =====
    @FindBy(css = ".mat-table tbody .type-row:first-child")
    private WebElement typeRaw;

    @FindBy(css = ".mat-table tbody .type-row:nth-child(2)")
    private WebElement SecondTypeRaw;

    @FindBy(id = "create_subType_button")
    private WebElement addSubTypeButton;

    @FindBy(css = ".mat-table tbody .subType-row:last-child .mat-column-options .mat-warn")
    private WebElement deleteSubTypeButton;

    @FindBy(id = "update_subType_button")
    private WebElement updateSubTypeButton;

    @FindBy(id = "close_subType_button")
    private WebElement closeSubTypeButton;

    @FindBy(id = "save_subType_button")
    private WebElement saveSubTypeButton;

    @FindBy(css = ".mat-dialog-container app-confirm-delete .delete-content div .mat-primary")
    private WebElement yesSubTypeButton;

    @FindBy(xpath = "//mat-dialog-container[@id='mat-dialog-0']/app-confirm-delete/mat-dialog-actions/button[2]")
    private WebElement noSubTypeButton;

    @FindBy(id = "subType_name")
    private WebElement subTypeInput;

    @FindBy(css = ".mat-simple-snackbar")
    private WebElement snackMessage;

    // ==== PAGINATION BUTTONS =====
    @FindBy(css = ".type-card .mat-card-actions app-pagination-bar div #pagination-button-next")
    private WebElement nextPaginationButtonType;

    @FindBy(css = ".type-card .mat-card-actions app-pagination-bar div #pagination-button-previous")
    private WebElement previousPaginationButtonType;

    @FindBy(css = ".sub-card .mat-card-actions app-pagination-bar div #pagination-button-next")
    private WebElement nextPaginationButtonSub;

    @FindBy(css = ".sub-card .mat-card-actions app-pagination-bar div #pagination-button-previous")
    private WebElement previousPaginationButtonSub;

    @FindBy(css = ".mat-table tbody .type-row:first-child .type_td")
    private WebElement firstTypeRowCell;

    @FindBy(css = ".mat-table tbody .subType-row:first-child .sub-td")
    private WebElement firstSubRowCell;

    public TypePage(WebDriver driver){
        this.driver = driver;
    }

    public void ensureTypeRawIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(typeRaw));
    }

    public void ensureSecondTypeRawIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(SecondTypeRaw));
    }

    public void ensureSnackbarIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(snackMessage));
    }

    public void ensureAddTypeButtonIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(addTypeButton));
    }

    public void ensureDeleteTypeButtonIsDisplayed() {
        //wait for add button to be present
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(deleteTypeButton));
    }

    public void ensureUpdateTypeButtonIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(updateTypeButton));
    }

    public void ensureCloseTypeButtonIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(closeTypeButton));
    }

    public void ensureSaveTypeButtonIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(saveTypeButton));
    }

    public void ensureYesTypeButtonIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(yesTypeButton));
    }

    public void ensureNoTypeButtonIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(noTypeButton));
    }

    public void ensureTypeNameIsDisplayed() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(typeNameInput));
    }

    public void ensureAddSubTypeButtonIsDisplayed(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(addSubTypeButton));
    }

    public void ensureDeleteSubTypeButtonIsDisplayed(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(deleteSubTypeButton));
    }

    public void ensureUpdateSubTypeButtonIsDisplayed(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(updateTypeButton));
    }

    public void ensureCloseSubTypeButtonIsDisplayed(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(closeSubTypeButton));
    }

    public void ensureSaveSubTypeButtonIsDisplayed(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(saveSubTypeButton));
    }

    public void ensureYesSubTypeButtonIsDisplayed(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(yesSubTypeButton));
    }

    public void ensureNoSubTypeButtonIsDisplayed(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(noSubTypeButton));
    }

    public void ensureSubTypeInputIsDisplayed(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(subTypeInput));
    }
    public void ensureTypeRowCount(int count){
        String selector = String.format(".type-row");
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.numberOfElementsToBe(
                        By.cssSelector(selector), count));
    }

    public void ensurePaginationTypeButtonIsDisplayed(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(nextPaginationButtonType));
    }

    public void ensurePaginationSubButtonIsDisplayed(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(nextPaginationButtonSub));
    }

}

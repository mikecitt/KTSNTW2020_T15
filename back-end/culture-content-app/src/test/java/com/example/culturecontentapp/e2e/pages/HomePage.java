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

    public HomePage(WebDriver driver){
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

    public void ensureIsDisplayed(){
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(applyFilterButton));
    }
}

package com.example.culturecontentapp.e2e.pages;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class SelectWebElement {

    private final WebDriver driver;
    private final String cssSelector;
    private final WebElement element;

    public SelectWebElement(WebDriver driver, String cssSelector){
        this.driver = driver;
        this.cssSelector = cssSelector;
        this.element = driver.findElement(By.cssSelector(cssSelector));
    }

    public void click(){
        this.element.click();
    }

    public void ensureDropdownItemCount(int count){
        String selector = String.format("%s-panel .mat-option", cssSelector);
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.numberOfElementsToBe(
                        By.cssSelector(selector), count));
    }

    public void chooseOption(int index) {
        String selector = String.format("%s-panel .mat-option:nth-child(%d)", cssSelector, index);
        driver.findElement(By.cssSelector(selector)).click();
    }
}

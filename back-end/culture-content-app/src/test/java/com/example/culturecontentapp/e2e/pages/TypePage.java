package com.example.culturecontentapp.e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TypePage {

    private WebDriver driver;

    @FindBy(id = "create_type_button")
    private WebElement addTypeButton;

    @FindBy(id = "delete_type_button")
    private WebElement deleteTypeButton;

    @FindBy(id = "update_type_button")
    private WebElement updateTypeButton;


}

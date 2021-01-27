package com.example.culturecontentapp.e2e.tests;

import com.example.culturecontentapp.e2e.pages.HomePage;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import static com.example.culturecontentapp.e2e.constants.SharedConstants.*;

public class MapTest {
    private WebDriver driver;
    private HomePage homePage;

    @Before
    public void setup(){
        System.setProperty(WEB_DRIVER, WEB_DRIVER_PATH);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.navigate().to(HOME_PATH);

        homePage = PageFactory.initElements(driver, HomePage.class);
    }

    @Test
    public void filterWithAllCorrectParams(){
        homePage.ensureIsDisplayed();

        homePage.getTypeSelect().click();
        homePage.getTypeSelect().ensureDropdownItemCount(2);
        homePage.getTypeSelect().chooseOption(1);

        homePage.getSubTypeSelect().click();
        homePage.getSubTypeSelect().ensureDropdownItemCount(2);
        homePage.getSubTypeSelect().chooseOption(1);

        homePage.getSearchInput().sendKeys("Subotica");
        homePage.getApplyFilterButton().click();

        homePage.ensureMarkerCount(2);
    }

    @Test
    public void filterWithBadSearchInput(){
        homePage.ensureIsDisplayed();

        homePage.getTypeSelect().click();
        homePage.getTypeSelect().ensureDropdownItemCount(2);
        homePage.getTypeSelect().chooseOption(1);

        homePage.getSubTypeSelect().click();
        homePage.getSubTypeSelect().ensureDropdownItemCount(2);
        homePage.getSubTypeSelect().chooseOption(1);

        homePage.getSearchInput().sendKeys("jklstdadasd");
        homePage.getApplyFilterButton().click();

        homePage.ensureMarkerCount(2);
    }

    @Test
    public void filterOnlyTypeSelected(){
        homePage.ensureIsDisplayed();

        homePage.getTypeSelect().click();
        homePage.getTypeSelect().ensureDropdownItemCount(2);
        homePage.getTypeSelect().chooseOption(1);

        homePage.getApplyFilterButton().click();
        homePage.ensureMarkerCount(NUMBER_OF_ALL_MARKERS);
    }

    @Test
    public void filterWithoutSearchInput(){
        homePage.ensureIsDisplayed();

        homePage.getTypeSelect().click();
        homePage.getTypeSelect().ensureDropdownItemCount(2);
        homePage.getTypeSelect().chooseOption(1);

        homePage.getSubTypeSelect().click();
        homePage.getSubTypeSelect().ensureDropdownItemCount(2);
        homePage.getSubTypeSelect().chooseOption(1);
        homePage.getApplyFilterButton().click();

        homePage.ensureMarkerCount(2);
    }

    @Test
    public void resetFilterForm(){
        homePage.ensureIsDisplayed();

        homePage.getTypeSelect().click();
        homePage.getTypeSelect().ensureDropdownItemCount(2);
        homePage.getTypeSelect().chooseOption(2);

        homePage.getSubTypeSelect().click();
        homePage.getSubTypeSelect().ensureDropdownItemCount(1);
        homePage.getSubTypeSelect().chooseOption(1);

        homePage.getSearchInput().sendKeys("Subotica");
        homePage.getApplyFilterButton().click();
        homePage.getResetFilterButton().click();

        homePage.ensureMarkerCount(NUMBER_OF_ALL_MARKERS);
    }
}

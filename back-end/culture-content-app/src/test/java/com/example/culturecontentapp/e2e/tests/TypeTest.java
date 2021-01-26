package com.example.culturecontentapp.e2e.tests;
import com.example.culturecontentapp.e2e.pages.LoginPage;
import com.example.culturecontentapp.e2e.pages.TypePage;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static com.example.culturecontentapp.e2e.constants.SharedConstants.*;
import static org.junit.Assert.assertEquals;

public class TypeTest {

    private WebDriver browser;

    TypePage typePage;

    LoginPage loginPage;

    @Before
    public void setup(){
        System.setProperty(WEB_DRIVER, WEB_DRIVER_PATH);
        browser = new ChromeDriver();
        browser.manage().window().maximize();
        browser.navigate().to(LOGIN_PATH);

        typePage = PageFactory.initElements(browser, TypePage.class);
        loginPage = PageFactory.initElements(browser, LoginPage.class);
    }


    public void testLogin(){
        loginPage.ensureIsDisplayed();
        loginPage.getUsernameInput().sendKeys(ADMIN_EMAIL);
        loginPage.getPasswordInput().sendKeys(ADMIN_PASS);
        loginPage.getLoginButton().click();
    }

    @Test
    public void create_validName_willReturnSucceed(){
        testLogin();
        sleep(2000);

        browser.findElement(By.cssSelector("#nav-types")).click();

        typePage.ensureAddTypeButtonIsDisplayed();
        typePage.getAddTypeButton().click();
        assertEquals(TYPES_PATH, browser.getCurrentUrl());

        int sizeBeforeCreating = browser.findElements(By.cssSelector(".type-row")).size();

        typePage.ensureTypeNameIsDisplayed();
        typePage.getTypeNameInput().sendKeys("NoviTip");
        typePage.getSaveTypeButton().click();
        sleep(1000);

        typePage.ensureSnackbarIsDisplayed();
        assertEquals("Created successfully", typePage.getSnackMessage().getText());

        typePage.ensureTypeRowCount(sizeBeforeCreating + 1);
    }
    @Test
    public void create_givenNameExists_willReturnError(){
        testLogin();
        sleep(2000);

        browser.findElement(By.cssSelector("#nav-types")).click();

        typePage.ensureAddTypeButtonIsDisplayed();
        typePage.getAddTypeButton().click();
        assertEquals(TYPES_PATH, browser.getCurrentUrl());

        int sizeBeforeCreating = browser.findElements(By.cssSelector(".type-row")).size();

        typePage.ensureTypeNameIsDisplayed();
        typePage.getTypeNameInput().sendKeys("Manifestacija");
        typePage.getSaveTypeButton().click();
        sleep(1000);

        typePage.ensureSnackbarIsDisplayed();
        assertEquals("Cultural offer type with given name already exists", typePage.getSnackMessage().getText());

        typePage.ensureTypeRowCount(sizeBeforeCreating);
    }
    @Test
    public void create_validParams_clickClose(){
        testLogin();
        sleep(2000);

        browser.findElement(By.cssSelector("#nav-types")).click();

        typePage.ensureAddTypeButtonIsDisplayed();
        typePage.getAddTypeButton().click();
        assertEquals(TYPES_PATH, browser.getCurrentUrl());
        //save size of type list
        int sizeBeforeCreating = browser.findElements(By.cssSelector(".type-row")).size();

        typePage.ensureTypeNameIsDisplayed();
        typePage.getTypeNameInput().sendKeys("NoviTip");
        typePage.getCloseTypeButton().click();
        sleep(1000);

        List<WebElement> data = browser.findElements(By.cssSelector(".type-row"));
        assertEquals(sizeBeforeCreating, data.size());
    }
    @Test
    public void delete_typeHasSubType_shouldReturnError(){
        testLogin();
        sleep(1000);

        browser.findElement(By.cssSelector("#nav-types")).click();
        assertEquals(TYPES_PATH, browser.getCurrentUrl());

        int sizeBeforeDeleting = browser.findElements(By.cssSelector(".type-row")).size();

        typePage.getDeleteTypeButton().click();
        typePage.ensureYesTypeButtonIsDisplayed();
        typePage.getYesTypeButton().click();

        typePage.ensureSnackbarIsDisplayed();
        assertEquals("Can't delete type", typePage.getSnackMessage().getText());

        List<WebElement> data = browser.findElements(By.cssSelector(".type-row"));
        assertEquals(sizeBeforeDeleting, data.size());
    }

    @Test
    public void delete_shouldReturnSuccess(){
        testLogin();
        sleep(1000);

        browser.findElement(By.cssSelector("#nav-types")).click();
        assertEquals(TYPES_PATH, browser.getCurrentUrl());

        int sizeBeforeDeleting = browser.findElements(By.cssSelector(".type-row")).size();

        typePage.getDeleteTypeButton().click();
        typePage.ensureYesTypeButtonIsDisplayed();
        typePage.getYesTypeButton().click();

        typePage.ensureSnackbarIsDisplayed();
        assertEquals("Deleted successfully", typePage.getSnackMessage().getText());

        List<WebElement> data = browser.findElements(By.cssSelector(".type-row"));
        assertEquals(sizeBeforeDeleting - 1, data.size());
    }

    @Test
    public void update_validName_shouldReturnSuccess(){
        testLogin();
        sleep(2000);

        browser.findElement(By.cssSelector("#nav-types")).click();
        assertEquals(TYPES_PATH, browser.getCurrentUrl());

        typePage.ensureUpdateTypeButtonIsDisplayed();
        typePage.getUpdateTypeButton().click();

        typePage.ensureTypeNameIsDisplayed();
        typePage.getTypeNameInput().clear();
        typePage.getTypeNameInput().sendKeys("Novi Tip");
        sleep(1000);
        typePage.getSaveTypeButton().click();

        typePage.ensureSnackbarIsDisplayed();
        assertEquals("Updated successfully", typePage.getSnackMessage().getText());

        List<WebElement> data = browser.findElements(By.cssSelector(".type_td"));
        WebElement updated = data.get(data.size() - 1);
        assertEquals(updated.getText(), "Novi Tip");
    }

    @Test
    public void update_validName_clickClose(){
        testLogin();
        sleep(2000);

        browser.findElement(By.cssSelector("#nav-types")).click();
        assertEquals(TYPES_PATH, browser.getCurrentUrl());

        typePage.ensureUpdateTypeButtonIsDisplayed();
        typePage.getUpdateTypeButton().click();

        typePage.ensureTypeNameIsDisplayed();
        typePage.getTypeNameInput().clear();
        typePage.getTypeNameInput().sendKeys("Novi Tip");
        sleep(1000);
        typePage.getCloseTypeButton().click();

        List<WebElement> data = browser.findElements(By.cssSelector(".type_td"));
        WebElement updated = data.get(0);
        assertEquals(updated.getText(), "Manifestacija");
    }

    @Test
    public void update_nameAlreadyExists_shouldReturnError(){
        testLogin();
        sleep(2000);

        browser.findElement(By.cssSelector("#nav-types")).click();
        assertEquals(TYPES_PATH, browser.getCurrentUrl());
        int beforeUpdating = browser.findElements(By.cssSelector(".type-row")).size();

        typePage.ensureUpdateTypeButtonIsDisplayed();
        typePage.getUpdateTypeButton().click();

        typePage.ensureTypeNameIsDisplayed();
        typePage.getTypeNameInput().clear();
        typePage.getTypeNameInput().sendKeys("Institucija");
        sleep(1000);
        typePage.getSaveTypeButton().click();

        typePage.ensureSnackbarIsDisplayed();
        assertEquals("Cultural offer type with given name already exists", typePage.getSnackMessage().getText());

        List<WebElement> data = browser.findElements(By.cssSelector(".type-row"));
        WebElement updated = data.get(data.size() - 1);
        assertEquals(data.size(), beforeUpdating);
    }

    public void sleep(int milliseconds){
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

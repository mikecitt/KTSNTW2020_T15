package com.example.culturecontentapp.e2e.tests;

import com.example.culturecontentapp.e2e.pages.LoginPage;
import com.example.culturecontentapp.e2e.pages.TypePage;
import lombok.AccessLevel;
import lombok.Getter;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static com.example.culturecontentapp.e2e.constants.SharedConstants.*;

public class SubTypeTest {

    @Getter(value= AccessLevel.PRIVATE)
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
    public void login(){
        loginPage.ensureIsDisplayed();
        loginPage.getUsernameInput().sendKeys(ADMIN_EMAIL);
        loginPage.getPasswordInput().sendKeys(ADMIN_PASS);
        loginPage.getLoginButton().click();
        sleep(1000);
    }

    @Test
    public void create_validName_shouldReturnSuccess(){
        login();
        browser.findElement(By.cssSelector("#nav-types")).click();

        typePage.ensureTypeRawIsDisplayed();
        typePage.getTypeRaw().click();
        typePage.ensureAddSubTypeButtonIsDisplayed();
        typePage.getAddSubTypeButton().click();

        assertEquals(TYPES_PATH, browser.getCurrentUrl());
        int sizeBeforeCreating = browser.findElements(By.cssSelector(".subType-row")).size();

        typePage.ensureSubTypeInputIsDisplayed();
        typePage.getSubTypeInput().sendKeys("Nova podkategorija");
        typePage.ensureSaveSubTypeButtonIsDisplayed();
        typePage.getSaveSubTypeButton().click();
        sleep(1000);

        typePage.ensureSnackbarIsDisplayed();
        assertEquals("Created successfully", typePage.getSnackMessage().getText());

        List<WebElement> rows = browser.findElements(By.cssSelector(".subType-row .sub-td"));
        WebElement created = rows.get(rows.size() - 1);
        assertEquals(sizeBeforeCreating + 1, rows.size());
        assertEquals("Nova podkategorija", created.getText());
    }

    @Test
    public void create_nameAlreadyExists_shouldReturnError(){
        login();
        browser.findElement(By.cssSelector("#nav-types")).click();

        typePage.ensureTypeRawIsDisplayed();
        typePage.getTypeRaw().click();
        typePage.ensureAddSubTypeButtonIsDisplayed();
        typePage.getAddSubTypeButton().click();

        assertEquals(TYPES_PATH, browser.getCurrentUrl());
        int sizeBeforeCreating = browser.findElements(By.cssSelector(".subType-row")).size();

        typePage.ensureSubTypeInputIsDisplayed();
        typePage.getSubTypeInput().sendKeys("Koncert");
        typePage.ensureSaveSubTypeButtonIsDisplayed();
        typePage.getSaveSubTypeButton().click();
        sleep(1000);

        typePage.ensureSnackbarIsDisplayed();
        assertEquals("Subtype with given name already exists", typePage.getSnackMessage().getText());

        List<WebElement> rows = browser.findElements(By.cssSelector(".subType-row"));
        assertEquals(sizeBeforeCreating, rows.size());
    }

    @Test
    public void create_validName_clickClose(){
        login();
        browser.findElement(By.cssSelector("#nav-types")).click();

        typePage.ensureTypeRawIsDisplayed();
        typePage.getTypeRaw().click();
        typePage.ensureAddSubTypeButtonIsDisplayed();
        typePage.getAddSubTypeButton().click();

        assertEquals(TYPES_PATH, browser.getCurrentUrl());
        int sizeBeforeCreating = browser.findElements(By.cssSelector(".subType-row")).size();

        typePage.ensureSubTypeInputIsDisplayed();
        typePage.getSubTypeInput().sendKeys("Nova podkategorija");
        typePage.ensureCloseSubTypeButtonIsDisplayed();
        typePage.getCloseSubTypeButton().click();
        sleep(1000);

        List<WebElement> rows = browser.findElements(By.cssSelector(".subType-row"));
        assertEquals(sizeBeforeCreating, rows.size());
    }

    @Test
    public void delete_shouldReturnSuccess(){
        login();
        browser.findElement(By.cssSelector("#nav-types")).click();

        typePage.ensureSecondTypeRawIsDisplayed();
        typePage.getSecondTypeRaw().click();
        typePage.ensureDeleteSubTypeButtonIsDisplayed();
        typePage.getDeleteSubTypeButton().click();
        sleep(500);

        assertEquals(TYPES_PATH, browser.getCurrentUrl());
        int sizeBeforeDeleting = browser.findElements(By.cssSelector(".subType-row")).size();

        typePage.ensureYesSubTypeButtonIsDisplayed();
        typePage.getYesSubTypeButton().click();

        typePage.ensureSnackbarIsDisplayed();
        assertEquals("Deleted successfully", typePage.getSnackMessage().getText());
        List<WebElement> data = browser.findElements(By.cssSelector(".subType-row"));
        assertEquals(sizeBeforeDeleting - 1, data.size());
    }

    @Test
    public void delete_subTypeHasOffer_shouldReturnError(){
        login();
        browser.findElement(By.cssSelector("#nav-types")).click();

        typePage.ensureTypeRawIsDisplayed();
        typePage.getTypeRaw().click();
        typePage.ensureDeleteSubTypeButtonIsDisplayed();
        typePage.getDeleteSubTypeButton().click();
        sleep(500);

        assertEquals(TYPES_PATH, browser.getCurrentUrl());
        int sizeBeforeDeleting = browser.findElements(By.cssSelector(".subType-row")).size();

        typePage.ensureYesSubTypeButtonIsDisplayed();
        typePage.getYesSubTypeButton().click();

        typePage.ensureSnackbarIsDisplayed();
        assertEquals("Sub type with given id has referenced cultural offers", typePage.getSnackMessage().getText());
        List<WebElement> data = browser.findElements(By.cssSelector(".subType-row"));
        assertEquals(sizeBeforeDeleting, data.size());
    }

    @Test
    public void update_validName_shouldReturnSuccess(){
        login();
        sleep(1000);

        browser.findElement(By.cssSelector("#nav-types")).click();
        assertEquals(TYPES_PATH, browser.getCurrentUrl());
        typePage.ensureTypeRawIsDisplayed();
        typePage.getTypeRaw().click();

        typePage.ensureUpdateSubTypeButtonIsDisplayed();
        typePage.getUpdateSubTypeButton().click();
        sleep(500);

        typePage.ensureSubTypeInputIsDisplayed();
        typePage.getSubTypeInput().clear();
        typePage.getSubTypeInput().sendKeys("Novi podtip");
        sleep(500);
        typePage.getSaveSubTypeButton().click();

        typePage.ensureSnackbarIsDisplayed();
        assertEquals("Updated successfully", typePage.getSnackMessage().getText());

        List<WebElement> data = browser.findElements(By.cssSelector(".sub-td"));
        WebElement updated = data.get(data.size() - 1);
        assertEquals( "Novi podtip",updated.getText());
    }

    @Test
    public void update_nameAlreadyExists_shouldReturnSuccess(){
        login();
        sleep(1000);

        browser.findElement(By.cssSelector("#nav-types")).click();
        assertEquals(TYPES_PATH, browser.getCurrentUrl());
        typePage.ensureTypeRawIsDisplayed();
        typePage.getTypeRaw().click();

        typePage.ensureUpdateSubTypeButtonIsDisplayed();
        typePage.getUpdateSubTypeButton().click();
        sleep(500);

        typePage.ensureSubTypeInputIsDisplayed();
        typePage.getSubTypeInput().clear();
        typePage.getSubTypeInput().sendKeys("Koncert");
        sleep(500);
        typePage.getSaveSubTypeButton().click();

        typePage.ensureSnackbarIsDisplayed();
        assertEquals("Subtype with given name already exists", typePage.getSnackMessage().getText());

        List<WebElement> data = browser.findElements(By.cssSelector(".sub-td"));
        WebElement updated = data.get(0);
        assertNotEquals( "Koncert",updated.getText());
    }

    @Test
    public void update_validName_clickClose(){
        login();
        sleep(1000);

        browser.findElement(By.cssSelector("#nav-types")).click();
        assertEquals(TYPES_PATH, browser.getCurrentUrl());
        typePage.ensureTypeRawIsDisplayed();
        typePage.getTypeRaw().click();

        typePage.ensureUpdateSubTypeButtonIsDisplayed();
        typePage.getUpdateSubTypeButton().click();
        sleep(500);

        typePage.ensureSubTypeInputIsDisplayed();
        typePage.getSubTypeInput().clear();
        typePage.getSubTypeInput().sendKeys("Neko ime");
        sleep(500);
        typePage.ensureCloseSubTypeButtonIsDisplayed();
        typePage.getCloseSubTypeButton().click();

        List<WebElement> data = browser.findElements(By.cssSelector(".sub-td"));
        WebElement updated = data.get(0);
        assertNotEquals( "Neko ime",updated.getText());
    }

    @Test
    public void a_pagination_shouldReturnSuccess(){
        login();
        browser.findElement(By.cssSelector("#nav-types")).click();
        browser.findElement(By.cssSelector(".mat-table tbody .type-row:nth-child(3)")).click();

        String firstCell = typePage.getFirstSubRowCell().getText();
        typePage.ensurePaginationSubButtonIsDisplayed();
        typePage.getNextPaginationButtonSub().click();
        sleep(500);
        String secondPageCell = typePage.getFirstSubRowCell().getText();
        assertNotEquals(firstCell, secondPageCell);

        typePage.getPreviousPaginationButtonSub().click();
        sleep(500);
        assertEquals(firstCell, typePage.getFirstSubRowCell().getText());
    }

    public void sleep(int milliseconds){
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

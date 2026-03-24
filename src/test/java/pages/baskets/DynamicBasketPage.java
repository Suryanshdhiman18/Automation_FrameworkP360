package pages.baskets;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class DynamicBasketPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By basketNameField =
            By.xpath("//input[@placeholder='Basket Name']");

    private By descriptionField =
            By.xpath("//textarea[@placeholder='Description']");

    private By attributeDropdown =
            By.xpath("//label[contains(text(),'Catalog')]/following::span[contains(@class,'k-input-value-text')][1]");

    private By departmentOption =
    		By.xpath("//span[normalize-space()='Department']");

    private By departmentDropdown =
            By.xpath("//rdui-multiselect[@id='multiSelect']//div[contains(@class,'multi-select-container')]");

//    private By firstDepartmentValue =
//            By.xpath("//label[contains(@class,'k-checkbox-label')]");

    private By createButton =
            By.xpath("//span[normalize-space()='Create']/ancestor::button");
    
    private By successPopup =
    	    By.xpath("//label[@class='rdui-label' and contains(normalize-space(.),'Basket created successfully')]");

    private By closeButton = 
    		By.xpath("//button[.//span[normalize-space()='Close']]");

    public DynamicBasketPage(WebDriver driver) {

        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void enterBasketName(String name) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(basketNameField))
                .sendKeys(name);
    }

    public void enterDescription(String description) {

        driver.findElement(descriptionField).sendKeys(description);
    }

    public void selectAttributeType() {

        wait.until(ExpectedConditions.elementToBeClickable(attributeDropdown)).click();

        wait.until(ExpectedConditions.elementToBeClickable(departmentOption)).click();
        
        driver.findElement(departmentOption).click();
    }

    public void selectDepartmentValues(String... values) {

        wait.until(ExpectedConditions.elementToBeClickable(departmentDropdown)).click();
        
        WebElement search =
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//input[contains(@placeholder,'Search')]")));
        

        for (String value : values) {

//            WebElement search =
//                wait.until(ExpectedConditions.visibilityOfElementLocated(
//                    By.xpath("//input[contains(@placeholder,'Search')]")));

            search.clear();
            search.sendKeys(value);

            By option =
            		By.xpath("//kendo-label[contains(normalize-space(),'" + value.toUpperCase() + "')]");

            WebElement element =
                wait.until(ExpectedConditions.visibilityOfElementLocated(option));

            ((JavascriptExecutor)driver)
                .executeScript("arguments[0].click();", element);
            
        }
        
        driver.findElement(basketNameField).click();
    }
    
    public void clickCreate() {

        wait.until(ExpectedConditions.elementToBeClickable(createButton)).click();
    }
    
    public boolean isSuccessPopupVisible() {

        return wait.until(ExpectedConditions.visibilityOfElementLocated(successPopup))
                .isDisplayed();
    }
    
    public void closeSuccessPopup() {

        WebElement close =
                wait.until(ExpectedConditions.elementToBeClickable(closeButton));

        close.click();
    }
}
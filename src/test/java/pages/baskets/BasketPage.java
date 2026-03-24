package pages.baskets;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.*;

public class BasketPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By createNewBasket =
            By.xpath("//button[.//span[contains(text(),'Create New Basket')]]");
    
//    -- New Locators
    
    private By basketCheckbox =
    	    By.xpath("//input[@type='checkbox' and @aria-label='Select Row']");

    private By exportButton =
    	    By.xpath("//button[.//span[@class='action-text' and normalize-space()='Export Baskets']]");

    private By deleteButton =
    	    By.xpath("//button[.//span[@class='action-text' and normalize-space()='Delete Baskets']]");

    private By confirmDeleteBtn =
    	    By.xpath("//button[.//span[@class='btn-text' and normalize-space()='Delete']]");

    private By deletePopup =
    	    By.xpath("//p[contains(@class,'deleteNote') and contains(normalize-space(.),'Deleting the basket linked')]");
    
    public BasketPage(WebDriver driver) {

        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void verifyBasketLoaded() {

        WebElement button =
                wait.until(ExpectedConditions.visibilityOfElementLocated(createNewBasket));

        if (!button.isDisplayed()) {
            throw new RuntimeException("Basket page did not load correctly");
        } 
    }

    public CreateBasketPopup clickCreateBasket() {

        wait.until(ExpectedConditions.elementToBeClickable(createNewBasket)).click();
        return new CreateBasketPopup(driver);
    }
    
//    -- New Methods
    
    public void selectFirstBasket() {

        WebElement checkbox =
            wait.until(ExpectedConditions.elementToBeClickable(basketCheckbox));

        checkbox.click();
    }
    
    public boolean isExportEnabled() {
        return driver.findElement(exportButton).isEnabled();
    }

    public boolean isDeleteEnabled() {
        return driver.findElement(deleteButton).isEnabled();
    }
    
    public void clickExport() {
    	WebElement export =
    			wait.until(ExpectedConditions.elementToBeClickable(exportButton));
    	export.click();
    }
    
    public void clickDelete() {
    	wait.until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
    }
    
    public void confirmDelete() {

        wait.until(ExpectedConditions.visibilityOfElementLocated(deletePopup));

        wait.until(ExpectedConditions.elementToBeClickable(confirmDeleteBtn)).click();
    }
    
    
}
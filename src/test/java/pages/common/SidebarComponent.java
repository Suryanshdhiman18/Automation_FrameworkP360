package pages.common;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class SidebarComponent {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators (adjust properly from UI)
    private By p360Icon = By.xpath("//li[.//a[normalize-space()='Product 360']]");
    
    private By dataMenu = By.xpath(
    	    "//div[contains(@class,'material-symbols-outlined') and normalize-space()='business_center']");
    
//    private By catalogIcon = By.xpath("//li[.//a[normalize-space()='My Catalog']]");
    private By catalogIcon = By.xpath("//a[normalize-space()='My Catalog']");
    
    
//    private By basketIcon = By.xpath("//li[.//a[normalize-space()='Baskets']]");
    private By basketIcon = By.xpath("//a[normalize-space()='Baskets']");

    public SidebarComponent(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void goToP360() {
        wait.until(ExpectedConditions.elementToBeClickable(p360Icon)).click();
    }

//    Catalog --
    
//    public void goToCatalog() {
//        wait.until(ExpectedConditions.elementToBeClickable(catalogIcon)).click();
//    }
    
    public void goToCatalog() {
    	
    	WebElement menu = wait.until(
    			ExpectedConditions.elementToBeClickable(dataMenu));
    	
    	menu.click();
    	
    	WebElement catalog = wait.until(
    			ExpectedConditions.elementToBeClickable(catalogIcon));
    	
    	catalog.click();
    			
    }
    
//    Basket --
    
//    public void goToBasket() {
//    	wait.until(ExpectedConditions.elementToBeClickable(basketIcon)).click();
//    }
    
    public void goToBasket() {

        // Click parent menu (this opens dropdown)
        WebElement menu = wait.until(
            ExpectedConditions.elementToBeClickable(dataMenu));

        menu.click();

        // Click Baskets option
        WebElement basket =
            wait.until(ExpectedConditions.elementToBeClickable(basketIcon));

        basket.click();
    }
}
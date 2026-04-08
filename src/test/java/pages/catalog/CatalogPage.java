package pages.catalog;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.LoggerUtil;


public class CatalogPage {

    private WebDriver driver;
    private WebDriverWait wait;
    
    private static final Logger log = LoggerUtil.getLogger(CatalogPage.class);

    private By uploadCatalogBtn = By.xpath("//button[.//span[contains(text(),'Upload Catalog')]]");
    private By cancelUploadBtn = By.xpath("//button//span[normalize-space()='Cancel Upload']");

    public CatalogPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void openUploadCatalogModal() {
    	
    	log.info("Opening Upload Catalog modal");

        // If previous upload validation panel exists, cancel it
        try {
            if (driver.findElement(cancelUploadBtn).isDisplayed()) {
                driver.findElement(cancelUploadBtn).click();
                Thread.sleep(1000); // small wait for UI reset
                
                log.info("Previous uplaod panel closed");
            }
        } catch (Exception ignored) {
        	log.info("No Previous uplaod panel found");
        }

        wait.until(ExpectedConditions.elementToBeClickable(uploadCatalogBtn)).click();
        
        log.info("Upload Catalog button clicked, modal should be open");
    }
}
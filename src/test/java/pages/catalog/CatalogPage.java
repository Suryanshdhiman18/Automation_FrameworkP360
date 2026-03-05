package pages.catalog;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CatalogPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // ===== Page Identifiers =====
    private By uploadCatalogButton = By.xpath(
            "//button[.//span[contains(text(),'Upload Catalog')]]"
    );
    
    public CatalogPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // ===== Verify Page Loaded =====
    public void verifyCatalogPageLoaded() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(uploadCatalogButton));
    }

    // ===== Click Upload =====
    public void clickUploadCatalog() {
        wait.until(ExpectedConditions.elementToBeClickable(uploadCatalogButton)).click();
    }
}
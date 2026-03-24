package pages.catalog;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CatalogPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By uploadCatalogBtn = By.xpath("//button[.//span[contains(text(),'Upload Catalog')]]");
    private By cancelUploadBtn = By.xpath("//button//span[normalize-space()='Cancel Upload']");

    public CatalogPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void openUploadCatalogModal() {

        // If previous upload validation panel exists, cancel it
        try {
            if (driver.findElement(cancelUploadBtn).isDisplayed()) {
                driver.findElement(cancelUploadBtn).click();
                Thread.sleep(1000); // small wait for UI reset
            }
        } catch (Exception ignored) {}

        wait.until(ExpectedConditions.elementToBeClickable(uploadCatalogBtn)).click();
    }
}
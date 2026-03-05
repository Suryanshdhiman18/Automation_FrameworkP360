package pages.catalog;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UploadCatalogModal {

    private WebDriver driver;
    private WebDriverWait wait;

    private By fileInput = By.xpath("//input[@type='file']");
    private By successToast = By.xpath("//*[contains(text(),'success')]");
    private By errorToast = By.xpath("//*[contains(text(),'error')]");

    
    public UploadCatalogModal(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void uploadFile(String filePath) {
        driver.findElement(fileInput).sendKeys(filePath);
    }

    public boolean isUploadSuccessful() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successToast)).isDisplayed();
    }

    public boolean isUploadFailed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorToast)).isDisplayed();
    }
}
package pages.catalog;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.*;

public class UploadCatalogModal {

    private WebDriver driver;
    private WebDriverWait wait;

    private By fileInput = By.xpath("//input[@type='file']");
    private By uploadButton = By.xpath("//button[contains(text(),'Upload')]");
    private By uploadValidRecordsBtn = By.xpath("//span[contains(text(),'Upload Valid Records')]");
    
    private By errorMessage = By.xpath("//*[contains(text(),'error')]");
    private By partialValidationMessage = By.xpath("//*[contains(text(),'records were successfully validated')]");
    private By downloadErrorBtn = By.xpath("//span[normalize-space()='Download Records with Errors']");

    private By validationMessage = By.xpath("//*[contains(text(),'catalog file was successfully validated')]");

    public UploadCatalogModal(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void uploadFile(String filePath) {
        wait.until(ExpectedConditions.presenceOfElementLocated(fileInput))
            .sendKeys(filePath);
    }

    public void clickUpload() {
        wait.until(ExpectedConditions.elementToBeClickable(uploadButton)).click();
    }

    public boolean isValidationSuccessful() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(validationMessage)).isDisplayed();
    }

    public void clickUploadValidRecords() {
        wait.until(ExpectedConditions.elementToBeClickable(uploadValidRecordsBtn)).click();

    }
    
    public boolean isUploadFailed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).isDisplayed();
    }

    public boolean isPartialUpload() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(partialValidationMessage)).isDisplayed();
    }

    public void downloadErrorRecords() {
        wait.until(ExpectedConditions.elementToBeClickable(downloadErrorBtn)).click();
    }
}
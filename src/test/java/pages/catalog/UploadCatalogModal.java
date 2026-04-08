package pages.catalog;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import org.slf4j.Logger;
import utils.LoggerUtil;

public class UploadCatalogModal {

    private WebDriver driver;
    private WebDriverWait wait;

    private static final Logger log = LoggerUtil.getLogger(UploadCatalogModal.class);

    // ===== Locators =====
    private By fileInput = By.xpath("//input[@type='file']");

    private By uploadButton = By.xpath("//button[contains(text(),'Upload')]");

    private By uploadValidRecordsBtn = By.xpath("//span[contains(text(),'Upload Valid Records')]");


    // ✅ FIXED LOCATORS
    private By errorBanner =
            By.xpath("//*[contains(text(),'Upload process was unsuccessful')]");

    private By partialValidationMessage = By.xpath("//*[contains(text(),'records were successfully validated')]");

    private By downloadErrorBtn =
            By.xpath("//span[normalize-space()='Download Records with Errors']/ancestor::button");

    private By cancelUploadBtn =
            By.xpath("//span[normalize-space()='Cancel Upload']/ancestor::button");

    private By validationMessage = By.xpath("//*[contains(text(),'catalog file was successfully validated')]");

    public UploadCatalogModal(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // ===== Upload File =====
    public void uploadFile(String filePath) {
        log.info("Uploading file: {}", filePath);

        wait.until(ExpectedConditions.presenceOfElementLocated(fileInput))
        
            .sendKeys(filePath);

        log.info("File path entered successfully");
    }

    // ===== Click Upload =====
    public void clickUpload() {
        log.info("Clicking Upload button");

        wait.until(ExpectedConditions.elementToBeClickable(uploadButton)).click();

        log.info("Upload button clicked");
    }

    // ===== VALID CASE =====
    public boolean isValidationSuccessful() {
        log.info("Checking if validation is successful");

        try {
            boolean result = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(validationMessage))
                    .isDisplayed();

            log.info("Validation success status: {}", result);
            return result;

        } catch (TimeoutException e) {
            log.error("Validation success message not found", e);
            return false;
        }
    }

    public void clickUploadValidRecords() {
        log.info("Clicking 'Upload Valid Records'");

        wait.until(ExpectedConditions.elementToBeClickable(uploadValidRecordsBtn)).click();

        log.info("'Upload Valid Records' clicked");
    }

    // ===== INVALID CASE (FIXED) =====
    public boolean isUploadFailed() {
        log.warn("Checking if upload failed");

        try {
            boolean result = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(errorBanner))
                    .isDisplayed();

            log.warn("Upload failed status: {}", result);
            return result;

        } catch (TimeoutException e) {
            log.error("Upload failure message not found", e);
            return false;
        }
    }

    // ===== PARTIAL CASE =====
    public boolean isPartialUpload() {
        log.info("Checking for partial upload");

        try {
            boolean result = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(partialValidationMessage))
                    .isDisplayed();

            log.info("Partial upload status: {}", result);
            return result;

        } catch (TimeoutException e) {
            log.error("Partial upload message not found", e);
            return false;
        }
    }

    // ===== DOWNLOAD ERROR RECORDS =====
    public void downloadErrorRecords() {
        log.info("Clicking 'Download Records with Errors'");

        WebElement btn = wait.until(
                ExpectedConditions.elementToBeClickable(downloadErrorBtn)
        );

        btn.click();

        log.info("Download triggered successfully");
    }

    // ===== CANCEL UPLOAD =====
    public void cancelUpload() {
        log.info("Clicking 'Cancel Upload'");

        WebElement btn = wait.until(
                ExpectedConditions.elementToBeClickable(cancelUploadBtn)
        );

        btn.click();

        log.info("Upload cancelled");
    }
    
    
}




//package pages.catalog;
//
//import java.time.Duration;
//import org.openqa.selenium.*;
//import org.openqa.selenium.support.ui.*;
//
//import org.slf4j.Logger;
//import utils.LoggerUtil;
//
//public class UploadCatalogModal {
//
//    private WebDriver driver;
//    private WebDriverWait wait;
//
//    private static final Logger log = LoggerUtil.getLogger(UploadCatalogModal.class);
//
//    // ===== Locators =====
//    private By fileInput = By.xpath("//input[@type='file']");
//
//    private By uploadButton = By.xpath("//button[contains(text(),'Upload')]");
//
//    private By uploadValidRecordsBtn = By.xpath("//span[contains(text(),'Upload Valid Records')]");
//
//    // ===== Messages =====
//    private By errorBanner =
//            By.xpath("//*[contains(text(),'Upload process was unsuccessful')]");
//
//    private By partialValidationMessage = By.xpath("//*[contains(text(),'records were successfully validated')]");
//
//    private By validationMessage =
//            By.xpath("//*[contains(text(),'catalog file was successfully validated')]");
//
//    // ===== Buttons =====
//    private By downloadErrorBtn =
//            By.xpath("//span[normalize-space()='Download Records with Errors']/ancestor::button");
//
//    private By cancelUploadBtn =
//            By.xpath("//span[normalize-space()='Cancel Upload']/ancestor::button");
//
//    // ===== Cancel Popup =====
////    private By cancelPopup =
////            By.xpath("//div[contains(@class,'k-dialog')]");
//    
//    private By cancelPopup = By.xpath("//div[contains(@class,'k-dialog') and .//span[text()='Cancel Upload']]");
//
////    private By confirmCancelBtn =
////            By.xpath("//div[contains(@class,'k-dialog')]//span[normalize-space()='Cancel Upload']/ancestor::button");
//    
//    private By confirmCancelBtn =
//          By.xpath(".//button[.//span[contains(@class,'btn-text') and contains(text(),'Cancel Upload')]]");
//    
////    private By confirmCancelBtn =
////            By.xpath("/html/body/rd-root/rdui-sidebar-navigation-layout/div/main/div[3]/div/app-my-catalog/rdui-confirm-popup[4]/kendo-dialog/div[2]/kendo-dialog-actions/div/button[2]");
//    
//    
//
//    private By goBackBtn =
//            By.xpath("//div[contains(@class,'k-dialog')]//span[normalize-space()='No, Go Back']/ancestor::button");
//
//    // ===== Constructor =====
//    public UploadCatalogModal(WebDriver driver) {
//        this.driver = driver;
//        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
//    }
//
//    // ===== Upload File =====
//    public void uploadFile(String filePath) {
//        log.info("Uploading file: {}", filePath);
//
//        wait.until(ExpectedConditions.presenceOfElementLocated(fileInput))
//            .sendKeys(filePath);
//
//        log.info("File path entered successfully");
//    }
//
//    // ===== Click Upload =====
//    public void clickUpload() {
//        log.info("Clicking Upload button");
//
//        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(uploadButton));
//        btn.click();
//
//        log.info("Upload button clicked");
//    }
//
//    // ===== VALID CASE =====
//    public boolean isValidationSuccessful() {
//        log.info("Checking if validation is successful");
//
//        try {
//            boolean result = wait.until(
//                    ExpectedConditions.visibilityOfElementLocated(validationMessage))
//                    .isDisplayed();
//
//            log.info("Validation success status: {}", result);
//            return result;
//
//        } catch (TimeoutException e) {
//            log.error("Validation success message not found", e);
//            return false;
//        }
//    }
//
//    public void clickUploadValidRecords() {
//        log.info("Clicking 'Upload Valid Records'");
//
//        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(uploadValidRecordsBtn));
//        btn.click();
//
//        log.info("'Upload Valid Records' clicked");
//    }
//
//    // ===== INVALID CASE =====
//    public boolean isUploadFailed() {
//        log.warn("Checking if upload failed");
//
//        try {
//            boolean result = wait.until(
//                    ExpectedConditions.visibilityOfElementLocated(errorBanner))
//                    .isDisplayed();
//
//            log.warn("Upload failed status: {}", result);
//            return result;
//
//        } catch (TimeoutException e) {
//            log.error("Upload failure message not found", e);
//            return false;
//        }
//    }
//
//    // ===== PARTIAL CASE =====
//    public boolean isPartialUpload() {
//        log.info("Checking for partial upload");
//
//        try {
//            boolean result = wait.until(
//                    ExpectedConditions.visibilityOfElementLocated(partialValidationMessage))
//                    .isDisplayed();
//
//            log.info("Partial upload status: {}", result);
//            return result;
//
//        } catch (TimeoutException e) {
//            log.error("Partial upload message not found", e);
//            return false;
//        }
//    }
//
//    // ===== DOWNLOAD ERROR RECORDS =====
//    public void downloadErrorRecords() {
//        log.info("Clicking 'Download Records with Errors'");
//
//        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(downloadErrorBtn));
//
//        try {
//            btn.click();
//        } catch (Exception e) {
//            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
//        }
//
//        log.info("Download triggered successfully");
//    }
//
//    // ===== CANCEL UPLOAD FLOW =====
//    public void cancelUploadAndConfirm() {
//
//        log.info("Clicking 'Cancel Upload'");
//
//        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(cancelUploadBtn));
//        btn.click();
//
//        log.info("Cancel upload button clicked");
//
//        handleCancelPopup();
//    }
//
//    // ===== HANDLE CANCEL POPUP =====
//    private void handleCancelPopup() {
//
//        log.info("Handling cancel upload confirmation popup");
//
//        try {
//            WebElement popup = wait.until(
//                    ExpectedConditions.visibilityOfElementLocated(cancelPopup)
//            );
//
//            if (popup.isDisplayed()) {
//
//                log.info("Cancel popup detected");
//
//                // 🔥 Use presence instead of clickable (more stable for Kendo UI)
//                WebElement confirmBtn = wait.until(
//                        ExpectedConditions.presenceOfElementLocated(confirmCancelBtn)
//                );
//
//                // 🔥 Ensure element is actually ready
//                wait.until(driver ->
//                        confirmBtn.isDisplayed() && confirmBtn.isEnabled()
//                );
//
//                // 🔥 Scroll into view (fix overlay/intercept issues)
//                ((JavascriptExecutor) driver)
//                        .executeScript("arguments[0].scrollIntoView({block:'center'});", confirmBtn);
//
//                // 🔥 Small wait for animation (important for modal UI)
//                Thread.sleep(500);
//
//                try {
//                    confirmBtn.click();
//                } catch (Exception e) {
//                    log.warn("Normal click failed, using JS click");
//
//                    ((JavascriptExecutor) driver)
//                            .executeScript("arguments[0].click();", confirmBtn);
//                }
//
//                log.info("Confirmed cancel upload");
//
//                // 🔥 Wait for popup to disappear
//                wait.until(ExpectedConditions.invisibilityOf(popup));
//
//                log.info("Popup closed successfully");
//            }
//
//        } catch (TimeoutException e) {
//            log.warn("Cancel upload popup not displayed");
//        } catch (Exception e) {
//            log.error("Error while handling cancel popup", e);
//            throw new RuntimeException(e);
//        }
//    }
//}
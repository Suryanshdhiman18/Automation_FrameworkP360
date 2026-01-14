package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ScreenshotUtil;

import java.time.Duration;

public class ProductDetailPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By viewDetailLink = By.xpath(
            "//div[@class='filter-block-content']//div[1]//div[1]//div[4]//a[1]"
    );

    private By productDescription = By.xpath("//span[@class='product-description']");
    private By upcValue = By.xpath("//label[normalize-space()='UPC:']/following-sibling::span");

    private By exportButton = By.xpath("//div[@class='p360-top-bar']//button[2]");
    private By toastMessage = By.xpath("//span[contains(@class,'message')]");

    public ProductDetailPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // ----------------- Navigation -----------------

    public void openFirstProductDetail() {
        int attempts = 0;

        while (attempts < 3) {
            try {
                WebElement link = wait.until(ExpectedConditions.elementToBeClickable(viewDetailLink));
                link.click();
                return;
            } catch (StaleElementReferenceException e) {
                attempts++;
            }
        }
        throw new RuntimeException("❌ Failed to click View Detail after retries");
    }

    // ----------------- Validations -----------------

    public String waitForProductDescription() {
        WebElement desc = wait.until(ExpectedConditions.visibilityOfElementLocated(productDescription));
        ScreenshotUtil.captureScreenshot(driver, "ProductDetail");
        return desc.getText().trim();
    }

    public String getUPC() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(upcValue))
                   .getText().trim();
    }

    // ----------------- Actions -----------------

    public void exportSearchResults() {
        try {
            // Wait for blocking toast to disappear (if present)
            try {
                wait.until(ExpectedConditions.invisibilityOfElementLocated(toastMessage));
            } catch (Exception ignored) {}

            WebElement exportBtn = wait.until(ExpectedConditions.presenceOfElementLocated(exportButton));

            // Scroll into view
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView(true);", exportBtn);
            Thread.sleep(400);

            // Try normal click
            try {
                wait.until(ExpectedConditions.elementToBeClickable(exportBtn)).click();
            } catch (ElementClickInterceptedException e) {
                // Fallback to JS click
                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].click();", exportBtn);
            }

            ScreenshotUtil.captureScreenshot(driver, "ExportClicked");

        } catch (Exception e) {
            throw new RuntimeException("❌ Export failed: " + e.getMessage());
        }
    }
}

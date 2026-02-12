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
    
    // --- Share feature locators ---
    private By shareButton = By.xpath("//rd-share-product-dialog//button[@class='btn p360-top-bar-actions-button']");
    private By shareModal = By.xpath("//span[contains(@class,'k-dialog-title') and contains(normalize-space(),'Share')]");

    private By shareDescriptionInput =
            By.xpath("//input[@placeholder='Enter description (max 50 characters)']");

    private By userDropdown =
            By.xpath("//div[@id='users']//div[@class='placeholder-container']");

    private By userSearchInput =
            By.xpath("//div[@role='dialog']//input[contains(@class,'k-input-inner') and @placeholder='Search']");
    
    private By userSearchConfirmation =
    		By.xpath("//input[@id='users_checkbox_0']");

    private By commentTextArea =
            By.xpath("//textarea[@placeholder='Write your comment']");

    private By shareConfirmButton =
            By.xpath("//span[normalize-space()='Share']");

    private By successToast =
            By.xpath("//*[contains(text(),'successfully shared') or contains(text(),'Shared')]");


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
    
    
    public void openShareDialog() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // 1️⃣ Wait for loader to disappear (important)
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.cssSelector("div.loader")
        ));

        // 2️⃣ Wait for share button to be clickable
        WebElement button = wait.until(
                ExpectedConditions.elementToBeClickable(shareButton)
        );

        // 3️⃣ Scroll to center (avoids overlap issues)
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                button
        );

        // 4️⃣ Click
        button.click();

        // 5️⃣ Wait for modal to appear
        wait.until(ExpectedConditions.visibilityOfElementLocated(shareModal));

        // 6️⃣ Screenshot
        ScreenshotUtil.captureScreenshot(driver, "ShareDialogOpened");
    }


    public void enterShareDescription(String description) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(shareDescriptionInput));
        input.clear();
        input.sendKeys(description);
    }

    public void selectUserToShare(String userName) {
    	
        // Open dropdown
        wait.until(ExpectedConditions.elementToBeClickable(userDropdown)).click();

        // Search for user
        WebElement search = wait.until(
            ExpectedConditions.elementToBeClickable(userSearchInput)
        );
        search.clear();
        search.sendKeys(userName);

        // Wait for username to appear
//        By userNameLocator = By.xpath("//div[contains(@class,'k-animation-container')]//li[normalize-space()='\" + userName + \"']");
//        wait.until(ExpectedConditions.visibilityOfElementLocated(userNameLocator));

        // Click the checkbox next to the username
        WebElement checkbox = wait.until(
            ExpectedConditions.elementToBeClickable(userSearchConfirmation)
        );
        checkbox.click();
        
     // After checkbox selection
        WebElement placeholder = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@id='users']//div[@class='placeholder-container']")
            )
        );

        placeholder.click();

        // Wait until dropdown is closed
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
            By.cssSelector("div.k-listview-content")
        ));

    }


    public void addOptionalComment(String comment) {
        WebElement commentBox = wait.until(ExpectedConditions.visibilityOfElementLocated(commentTextArea));
        commentBox.sendKeys(comment);
    }

    public void clickShareConfirm() {
        wait.until(ExpectedConditions.elementToBeClickable(shareConfirmButton)).click();
    }

    public void verifyShareSuccess() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(successToast));
        ScreenshotUtil.captureScreenshot(driver, "ProductSharedSuccessfully");
    }
    

}

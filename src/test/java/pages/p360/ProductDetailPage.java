package pages.p360;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import utils.ScreenshotUtil;
import utils.GridUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class ProductDetailPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private GridUtils gridUtils;

    // ✅ Logger
    private static final Logger log = LoggerFactory.getLogger(ProductDetailPage.class);

    // Locators
    private By gridContainer = By.cssSelector("kendo-grid");

    private By viewDetailLink = By.xpath(
            "//div[@class='filter-block-content']//div[1]//div[1]//div[4]//a[1]"
    );

    private By productDescription = By.xpath("//span[contains(@class,'product-description')]");
    private By upcValue = By.xpath("//label[normalize-space()='UPC:']/following-sibling::span");

    private By exportButton = By.xpath("//div[@class='p360-top-bar']//button[2]");
    private By toastMessage = By.xpath("//span[contains(@class,'message')]");

    // Share locators
    private By shareButton = By.xpath("//rd-share-product-dialog//button[@class='btn p360-top-bar-actions-button']");
    private By shareModal = By.xpath("//span[contains(@class,'k-dialog-title') and contains(normalize-space(),'Share')]");
    private By shareDescriptionInput = By.xpath("//input[@placeholder='Enter description (max 50 characters)']");
    private By userDropdown = By.xpath("//div[@id='users']//div[@class='placeholder-container']");
    private By userSearchInput = By.xpath("//div[@role='dialog']//input[contains(@class,'k-input-inner') and @placeholder='Search']");
    private By userSearchConfirmation = By.xpath("//input[@id='users_checkbox_0']");
    private By commentTextArea = By.xpath("//textarea[@placeholder='Write your comment']");
    private By shareConfirmButton = By.xpath("//span[normalize-space()='Share']");
    private By successToast = By.xpath("//*[contains(text(),'successfully shared') or contains(text(),'Shared')]");

    public ProductDetailPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.gridUtils = new GridUtils(driver);
    }

    // ----------------- Navigation -----------------

    public void openFirstProductDetail() {
        log.info("Opening first product detail");

        int attempts = 0;

        while (attempts < 3) {
            try {
                WebElement link = wait.until(ExpectedConditions.elementToBeClickable(viewDetailLink));
                link.click();
                log.info("Clicked on View Detail");
                return;
            } catch (StaleElementReferenceException e) {
                attempts++;
                log.warn("Retrying click on View Detail (attempt {})", attempts);
            }
        }

        log.error("Failed to click View Detail after retries");
        throw new RuntimeException("❌ Failed to click View Detail after retries");
    }

    // ----------------- Validations -----------------

    public String waitForProductDescription() {

        log.info("Waiting for product description");

        Wait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(60))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);

        WebElement desc = fluentWait.until(driver -> {
            List<WebElement> elements = driver.findElements(productDescription);

            if (!elements.isEmpty()) {
                WebElement el = elements.get(0);
                String text = el.getText().trim();

                if (el.isDisplayed() && !text.isEmpty()) {
                    return el;
                }
            }
            return null;
        });

        ScreenshotUtil.captureScreenshot(driver, "ProductDetail");
        log.info("Product description loaded: {}", desc.getText().trim());

        return desc.getText().trim();
    }

    public String getUPC() {
        log.info("Fetching UPC value");

        String upc = wait.until(ExpectedConditions.visibilityOfElementLocated(upcValue))
                .getText().trim();

        log.info("UPC value: {}", upc);
        return upc;
    }

    // ----------------- Actions -----------------

    public void exportSearchResults() {
        log.info("Attempting to export search results");

        try {
            try {
                wait.until(ExpectedConditions.invisibilityOfElementLocated(toastMessage));
            } catch (Exception ignored) {}

            WebElement exportBtn = wait.until(ExpectedConditions.presenceOfElementLocated(exportButton));

            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView(true);", exportBtn);

            Thread.sleep(400);

            try {
                wait.until(ExpectedConditions.elementToBeClickable(exportBtn)).click();
                log.info("Clicked export button normally");
            } catch (ElementClickInterceptedException e) {
                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].click();", exportBtn);
                log.warn("Used JS click for export button");
            }

            ScreenshotUtil.captureScreenshot(driver, "ExportClicked");

        } catch (Exception e) {
            log.error("Export failed: {}", e.getMessage());
            throw new RuntimeException("❌ Export failed: " + e.getMessage());
        }
    }

    // ----------------- Share Feature -----------------

    public void openShareDialog() {

        log.info("Opening Share dialog");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.loader")));

        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(shareButton)); 

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                button
        );

        button.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(shareModal));

        ScreenshotUtil.captureScreenshot(driver, "ShareDialogOpened");
        log.info("Share dialog opened successfully");
    }

    public void enterShareDescription(String description) {
        log.info("Entering share description");

        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(shareDescriptionInput));
        input.clear();
        input.sendKeys(description);
    }

    public void selectUserToShare(String userName) {

        log.info("Selecting user to share: {}", userName);

        wait.until(ExpectedConditions.elementToBeClickable(userDropdown)).click();

        WebElement search = wait.until(ExpectedConditions.elementToBeClickable(userSearchInput));
        search.clear();
        search.sendKeys(userName);

        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(userSearchConfirmation));
        checkbox.click();

        wait.until(ExpectedConditions.elementToBeClickable(userDropdown)).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.cssSelector("div.k-listview-content")
        ));

        log.info("User selected successfully");
    }

    public void addOptionalComment(String comment) {
        log.info("Adding comment in share dialog");

        WebElement commentBox = wait.until(ExpectedConditions.visibilityOfElementLocated(commentTextArea));
        commentBox.sendKeys(comment);
    }

    public void clickShareConfirm() {
        log.info("Clicking Share confirm button");

        wait.until(ExpectedConditions.elementToBeClickable(shareConfirmButton)).click();
    }

    public void verifyShareSuccess() {
        log.info("Verifying share success message");

        wait.until(ExpectedConditions.visibilityOfElementLocated(successToast));
        ScreenshotUtil.captureScreenshot(driver, "ProductSharedSuccessfully");

        log.info("Product shared successfully");
    }

    // ----------------- Grid -----------------

    public void waitForGridToLoad() {
        log.info("Waiting for grid to load");

        wait.until(ExpectedConditions.visibilityOfElementLocated(gridContainer));

        log.info("Grid loaded successfully");
    }

    public Map<String, Map<String, String>> getGridData() {
        log.info("Extracting grid data");

        Map<String, Map<String, String>> data = gridUtils.extractGridData();

        log.info("Grid data extracted successfully");
        return data;
    }
}
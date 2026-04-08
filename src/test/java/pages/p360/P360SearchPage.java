package pages.p360;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class P360SearchPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // ✅ Logger added
    private static final Logger log = LoggerFactory.getLogger(P360SearchPage.class);

    // Locators
    private By searchInput = By.xpath(
        "//input[contains(@placeholder,'Digiorno') and contains(@class,'k-input-inner')]"
    );

    private By searchButton = By.xpath("//span[normalize-space()='Search']");

    public P360SearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void verifyP360Loaded() {
        log.info("Verifying P360 page is loaded");

        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));

        if (!input.isDisplayed()) {
            log.error("P360 page did not load correctly");
            throw new RuntimeException("P360 page did not load correctly");
        }

        log.info("P360 page loaded successfully");
    }

    public void searchProduct(String productName) {
        log.info("Searching for product: {}", productName);

        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        input.clear();
        input.sendKeys(productName);
        input.sendKeys(Keys.ENTER);

        log.info("Search triggered using ENTER key");
    }

    public void clickSearch() {
        log.info("Clicking Search button");

        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();

        log.info("Search button clicked");
    }
}
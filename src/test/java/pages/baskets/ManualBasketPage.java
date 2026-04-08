package pages.baskets;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.*;

import org.slf4j.Logger;
import utils.LoggerUtil;

public class ManualBasketPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private static final Logger log = LoggerUtil.getLogger(ManualBasketPage.class);

    private By basketName =
            By.xpath("//input[contains(@placeholder,'Basket Name')]");

    private By description =
            By.xpath("//textarea[contains(@placeholder,'Description')]");

    private By createButton =
            By.xpath("//span[@class='btn-text' and normalize-space()='Create']");

    private By duplicateError =
            By.xpath("//*[contains(text(),'already exists')]");

    public ManualBasketPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // ------------------ INPUT ACTIONS ------------------

    public void enterBasketName(String name) {

        log.info("Entering basket name: {}", name);

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(basketName))
                .sendKeys(name);

            log.info("Basket name entered successfully");

        } catch (Exception e) {
            log.error("Failed to enter basket name: {}", name, e);
            throw e;
        }
    }

    public void enterDescription(String text) {

        log.info("Entering basket description");

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(description))
                .sendKeys(text);

            log.info("Basket description entered successfully");

        } catch (Exception e) {
            log.error("Failed to enter basket description", e);
            throw e;
        }
    }

    // ------------------ ACTION ------------------

    public void clickCreateBasket() {

        log.info("Clicking Create Basket button");

        try {
            wait.until(ExpectedConditions.elementToBeClickable(createButton)).click();

            log.info("Create Basket action triggered");

        } catch (Exception e) {
            log.error("Failed to click Create Basket button", e);
            throw e;
        }
    }

    // ------------------ VALIDATION ------------------

    public boolean isDuplicateErrorDisplayed() {

        log.info("Checking for duplicate basket error");

        try {
            boolean isDisplayed = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(duplicateError))
                    .isDisplayed();

            log.info("Duplicate error displayed: {}", isDisplayed);

            return isDisplayed;

        } catch (Exception e) {
            log.warn("Duplicate error message not displayed");
            return false;
        }
    }
}
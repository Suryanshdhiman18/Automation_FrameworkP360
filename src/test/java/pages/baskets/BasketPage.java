package pages.baskets;

import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import org.slf4j.Logger;
import utils.LoggerUtil;

public class BasketPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private static final Logger log = LoggerUtil.getLogger(BasketPage.class);

    private By createNewBasket =
            By.xpath("//button[.//span[contains(text(),'Create New Basket')]]");

    private By basketCheckbox =
            By.xpath("//input[@type='checkbox' and @aria-label='Select Row']");

    private By exportButton =
            By.xpath("//button[.//span[@class='action-text' and normalize-space()='Export Baskets']]");

    private By deleteButton =
            By.xpath("//button[.//span[@class='action-text' and normalize-space()='Delete Baskets']]");

    private By confirmDeleteBtn =
            By.xpath("//button[.//span[@class='btn-text' and normalize-space()='Delete']]");

    private By deletePopup =
            By.xpath("//p[contains(@class,'deleteNote')]");

    private By viewDeletedBasket =
            By.xpath("");

    public BasketPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // ------------------ VALIDATION ------------------

    public void verifyBasketLoaded() {

        log.info("Verifying Basket page is loaded");

        try {
            WebElement button = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(createNewBasket));

            if (!button.isDisplayed()) {
                throw new RuntimeException("Basket page not visible");
            }

            log.info("Basket page loaded successfully");

        } catch (Exception e) {
            log.error("Basket page failed to load", e);
            throw e;
        }
    }

    // ------------------ ACTIONS ------------------

    public CreateBasketPopup clickCreateBasket() {

        log.info("Clicking 'Create New Basket' button");

        try {
            wait.until(ExpectedConditions.elementToBeClickable(createNewBasket)).click();

            log.info("Create Basket popup opened successfully");

        } catch (Exception e) {
            log.error("Failed to click 'Create New Basket' button", e);
            throw e;
        }

        return new CreateBasketPopup(driver);
    }

    public void selectFirstBasket() {

        log.info("Selecting first available basket");

        try {
            WebElement checkbox =
                    wait.until(ExpectedConditions.elementToBeClickable(basketCheckbox));

            checkbox.click();

            log.info("Basket checkbox selected");

        } catch (Exception e) {
            log.error("Failed to select basket checkbox", e);
            throw e;
        }
    }

    public boolean isExportEnabled() {

        try {
            boolean enabled = driver.findElement(exportButton).isEnabled();

            log.info("Export button enabled status: {}", enabled);

            return enabled;

        } catch (Exception e) {
            log.error("Failed to check Export button state", e);
            throw e;
        }
    }

    public boolean isDeleteEnabled() {

        try {
            boolean enabled = driver.findElement(deleteButton).isEnabled();

            log.info("Delete button enabled status: {}", enabled);

            return enabled;

        } catch (Exception e) {
            log.error("Failed to check Delete button state", e);
            throw e;
        }
    }

    public void clickExport() {

        log.info("Triggering Export Baskets action");

        try {
            WebElement export =
                    wait.until(ExpectedConditions.elementToBeClickable(exportButton));

            export.click();

            log.info("Export action executed successfully");

        } catch (Exception e) {
            log.error("Failed to trigger Export action", e);
            throw e;
        }
    }

    public void clickDelete() {

        log.info("Triggering Delete Baskets action");

        try {
            wait.until(ExpectedConditions.elementToBeClickable(deleteButton)).click();

            log.info("Delete button clicked successfully");

        } catch (Exception e) {
            log.error("Failed to click Delete button", e);
            throw e;
        }
    }

    public void confirmDelete() {

        log.info("Confirming basket deletion");

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(deletePopup));

            wait.until(ExpectedConditions.elementToBeClickable(confirmDeleteBtn)).click();

            log.info("Basket deletion confirmed successfully");

        } catch (Exception e) {
            log.error("Failed to confirm basket deletion", e);
            throw e;
        }
    }

    public void viewBaskets() {

        log.info("Checking Deleted Baskets");

        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(viewDeletedBasket));

            wait.until(ExpectedConditions.elementToBeClickable(viewDeletedBasket)).click();

            log.info("Deleted Baskets viewed successfully");

        } catch (Exception e) {
            log.error("Failed to open View Deleted Baskets");
        }
    }
}
package pages.baskets;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import org.slf4j.Logger;
import utils.LoggerUtil;

public class DynamicBasketPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private static final Logger log = LoggerUtil.getLogger(DynamicBasketPage.class);

    private By basketNameField =
            By.xpath("//input[@placeholder='Basket Name']");

    private By descriptionField =
            By.xpath("//textarea[@placeholder='Description']");

    private By attributeDropdown =
            By.xpath("//label[contains(text(),'Catalog')]/following::span[contains(@class,'k-input-value-text')][1]");

    private By departmentOption =
            By.xpath("//span[normalize-space()='Department']");

    private By departmentDropdown =
            By.xpath("//rdui-multiselect[@id='multiSelect']//div[contains(@class,'multi-select-container')]");

    private By createButton =
            By.xpath("//span[normalize-space()='Create']/ancestor::button");

    private By successPopup =
            By.xpath("//label[contains(normalize-space(.),'Basket created successfully')]");

    private By closeButton =
            By.xpath("//button[.//span[normalize-space()='Close']]");

    public DynamicBasketPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // ------------------ INPUT ------------------

    public void enterBasketName(String name) {

        log.info("Entering dynamic basket name: {}", name);

        try {
            WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(basketNameField));
            input.clear();
            input.sendKeys(name);

            log.info("Basket name entered successfully");

        } catch (Exception e) {
            log.error("Failed to enter basket name: {}", name, e);
            throw e;
        }
    }

    public void enterDescription(String description) {

        log.info("Entering basket description");

        try {
            WebElement desc = wait.until(ExpectedConditions.visibilityOfElementLocated(descriptionField));
            desc.clear();
            desc.sendKeys(description);

            log.info("Basket description entered successfully");

        } catch (Exception e) {
            log.error("Failed to enter basket description", e);
            throw e;
        }
    }

    // ------------------ DROPDOWN FLOW ------------------

    public void selectAttributeType() {

        log.info("Selecting attribute type: Department");

        try {
            wait.until(ExpectedConditions.elementToBeClickable(attributeDropdown)).click();

            WebElement option =
                    wait.until(ExpectedConditions.elementToBeClickable(departmentOption));

            option.click();

            log.info("Attribute 'Department' selected successfully");

        } catch (Exception e) {
            log.error("Failed to select attribute type: Department", e);
            throw e;
        }
    }

    public void selectDepartmentValues(String... values) {

        log.info("Selecting department values: {}", (Object) values);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(departmentDropdown)).click();

            WebElement search =
                    wait.until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//input[contains(@placeholder,'Search')]")));

            for (String value : values) {

                log.info("Selecting department value: {}", value);

                search.clear();
                search.sendKeys(value);

                By option =
                        By.xpath("//kendo-label[contains(normalize-space(),'" + value.toUpperCase() + "')]");

                WebElement element =
                        wait.until(ExpectedConditions.visibilityOfElementLocated(option));

                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].click();", element);

                log.info("Selected value: {}", value);
            }

            // Close dropdown safely
            driver.findElement(basketNameField).click();

            log.info("All department values selected successfully");

        } catch (Exception e) {
            log.error("Failed to select department values", e);
            throw e;
        }
    }

    // ------------------ ACTION ------------------

    public void clickCreate() {

        log.info("Clicking Create button for dynamic basket");

        try {
            wait.until(ExpectedConditions.elementToBeClickable(createButton)).click();

            log.info("Create action triggered successfully");

        } catch (Exception e) {
            log.error("Failed to click Create button", e);
            throw e;
        }
    }

    // ------------------ VALIDATION ------------------

    public boolean isSuccessPopupVisible() {

        log.info("Verifying dynamic basket creation success popup");

        try {
            boolean visible = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(successPopup))
                    .isDisplayed();

            log.info("Success popup visible: {}", visible);

            return visible;

        } catch (Exception e) {
            log.error("Success popup not visible", e);
            return false;
        }
    }

    public void closeSuccessPopup() {

        log.info("Closing success popup");

        try {
            WebElement close =
                    wait.until(ExpectedConditions.elementToBeClickable(closeButton));

            close.click();

            log.info("Success popup closed successfully");

        } catch (Exception e) {
            log.error("Failed to close success popup", e);
            throw e;
        }
    }
}
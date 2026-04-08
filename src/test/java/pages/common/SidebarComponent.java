package pages.common;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import org.slf4j.Logger;
import utils.LoggerUtil;

import java.time.Duration;

public class SidebarComponent {

    private WebDriver driver;
    private WebDriverWait wait;

    // ✅ Use LoggerUtil (consistency across framework)
    private static final Logger log = LoggerUtil.getLogger(SidebarComponent.class);

    // Locators
    private By p360Icon = By.xpath("//li[.//a[normalize-space()='Product 360']]");

    private By dataMenu = By.xpath(
        "//div[contains(@class,'material-symbols-outlined') and normalize-space()='business_center']"
    );

    private By catalogIcon = By.xpath("//a[normalize-space()='My Catalog']");
    private By basketIcon = By.xpath("//a[normalize-space()='Baskets']");

    public SidebarComponent(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // -------- P360 --------
    public void goToP360() {

        log.info("Navigating to Product 360 module");

        try {
            wait.until(ExpectedConditions.elementToBeClickable(p360Icon)).click();
            log.info("Product 360 page opened successfully");

        } catch (Exception e) {
            log.error("Failed to navigate to Product 360", e);
            throw e;
        }
    }

    // -------- Catalog --------
    public void goToCatalog() {

        log.info("Navigating to Catalog module");

        try {
            WebElement menu = wait.until(
                ExpectedConditions.elementToBeClickable(dataMenu)
            );

            menu.click();
            log.info("Data menu expanded");

            WebElement catalog = wait.until(
                ExpectedConditions.elementToBeClickable(catalogIcon)
            );

            catalog.click();
            log.info("Catalog page opened successfully");

        } catch (Exception e) {
            log.error("Failed to navigate to Catalog", e);
            throw e;
        }
    }

    // -------- Basket --------
    public void goToBasket() {

        log.info("Navigating to Basket module");

        try {
            WebElement menu = wait.until(
                ExpectedConditions.elementToBeClickable(dataMenu)
            );

            menu.click();
            log.info("Data menu expanded");

            WebElement basket = wait.until(
                ExpectedConditions.elementToBeClickable(basketIcon)
            );

            basket.click();
            log.info("Basket page opened successfully");

        } catch (Exception e) {
            log.error("Failed to navigate to Basket", e);
            throw e;
        }
    }
}
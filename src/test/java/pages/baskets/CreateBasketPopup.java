package pages.baskets;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.*;

import org.slf4j.Logger;
import utils.LoggerUtil;

public class CreateBasketPopup {

    private static final Logger log = LoggerUtil.getLogger(CreateBasketPopup.class);

    private WebDriver driver;
    private WebDriverWait wait;

    private By manualBasket =
            By.xpath("//span[contains(text(),'Create Manual Basket')]");

    private By dynamicBasket =
            By.xpath("//span[contains(text(),'Create Dynamic Basket')]");

    public CreateBasketPopup(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public ManualBasketPage selectManualBasket() {

        log.info("Selecting Manual Basket option");
        log.debug("Locator: {}", manualBasket);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(manualBasket)).click();

            log.info("Manual Basket option selected successfully");

        } catch (Exception e) {
            log.error("Failed to select Manual Basket option", e);
            throw e;
        }

        return new ManualBasketPage(driver);
    }

    public DynamicBasketPage selectDynamicBasket() {

        log.info("Selecting Dynamic Basket option");

        try {
            wait.until(ExpectedConditions.elementToBeClickable(dynamicBasket)).click();

            log.info("Dynamic Basket option selected successfully");

        } catch (Exception e) {
            log.error("Failed to select Dynamic Basket option", e);
            throw e;
        }

        return new DynamicBasketPage(driver);
    }
}
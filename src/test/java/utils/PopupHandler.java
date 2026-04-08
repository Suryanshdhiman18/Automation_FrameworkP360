package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class PopupHandler {

    private static final Logger log = LoggerFactory.getLogger(PopupHandler.class);

    public static void handleReleaseNotesPopup(WebDriver driver) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        try {
            log.info("Checking for Release Notes popup...");

            // ✅ Stable locator (title-based)
            By popupTitle = By.xpath(
                "//span[contains(@class,'k-dialog-title') and contains(text(),'Release Notes')]"
            );

            // Wait for popup
            WebElement popup = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(popupTitle)
            );

            if (popup.isDisplayed()) {

                log.info("Release Notes popup detected");

                // ✅ Correct close icon locator (based on your DOM)
                WebElement closeBtn = wait.until(
                        ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//kendo-svgicon[contains(@class,'k-svg-i-x')]/ancestor::button")
                        )
                );

                // ✅ JS click (important for Kendo)
                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].click();", closeBtn);

                log.info("Clicked close (X) on popup");

                // ✅ Wait until popup disappears
                wait.until(ExpectedConditions.invisibilityOf(popup));

                log.info("Popup closed successfully");
            }

        } catch (TimeoutException e) {

            log.info("Release Notes popup not displayed — continuing execution");

        } catch (Exception e) {

            log.warn("Popup handling failed but test will continue", e);
        }
    }
}
package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class PopupHandler {

	public static void handleReleaseNotesPopup(WebDriver driver) {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	    try {
	        By popupHeader = By.xpath(
	            "//span[contains(@class,'k-dialog-title') and contains(text(),'Release Notes')]"
	        );

	        By closeButton = By.xpath(
	            "//kendo-dialog//button[contains(@class,'k-window-close')]"
	        );

	        // Wait for popup to appear
	        WebElement popup = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(popupHeader)
	        );

	        if (popup.isDisplayed()) {

	            WebElement close = wait.until(
	                    ExpectedConditions.elementToBeClickable(closeButton)
	            );

	            // JS click (safe for Kendo)
	            ((JavascriptExecutor) driver)
	                    .executeScript("arguments[0].click();", close);

	            System.out.println("ℹ️ Release Notes popup handled.");
	        }

	    } catch (TimeoutException e) {
	        System.out.println("ℹ️ Release Notes popup not displayed.");
	    }
	}
}
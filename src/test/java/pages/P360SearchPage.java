package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class P360SearchPage {

    private WebDriver driver;
    private WebDriverWait wait;

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
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        if (!input.isDisplayed()) {
            throw new RuntimeException("P360 page did not load correctly");
        }
    }

    public void searchProduct(String productName) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        input.clear();
        input.sendKeys(productName);
        input.sendKeys(Keys.ENTER);
    }

    public void clickSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }
}


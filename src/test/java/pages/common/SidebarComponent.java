package pages.common;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class SidebarComponent {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators (adjust properly from UI)
    private By p360Icon = By.xpath("//li[.//a[normalize-space()='Product 360']]");
    private By catalogIcon = By.xpath("//li[.//a[normalize-space()='My Catalog']]");

    public SidebarComponent(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void goToP360() {
        wait.until(ExpectedConditions.elementToBeClickable(p360Icon)).click();
    }

    public void goToCatalog() {
        wait.until(ExpectedConditions.elementToBeClickable(catalogIcon)).click();
    }
}
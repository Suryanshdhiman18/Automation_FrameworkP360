package pages.baskets;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.*;

public class CreateBasketPopup {

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

        wait.until(ExpectedConditions.elementToBeClickable(manualBasket)).click();

        return new ManualBasketPage(driver);
    }

    public DynamicBasketPage selectDynamicBasket() {

        wait.until(ExpectedConditions.elementToBeClickable(dynamicBasket)).click();

        return new DynamicBasketPage(driver);
    }
}
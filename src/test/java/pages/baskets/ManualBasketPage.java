package pages.baskets;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.*;

public class ManualBasketPage {

    private WebDriver driver;
    private WebDriverWait wait;

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

    public void enterBasketName(String name) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(basketName))
            .sendKeys(name);
    }

    public void enterDescription(String text) {

        driver.findElement(description).sendKeys(text);
    }

    public void clickCreateBasket() {

        wait.until(ExpectedConditions.elementToBeClickable(createButton)).click();
    }

    public boolean isDuplicateErrorDisplayed() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(duplicateError))
                .isDisplayed();
    }
}
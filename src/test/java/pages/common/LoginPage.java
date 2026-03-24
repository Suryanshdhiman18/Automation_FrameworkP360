package pages.common;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Scanner;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // ===== Constructor =====
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // ===== Locators (same as before, just structured) =====
    private By emailField = By.id("i0116");
    private By nextButton = By.id("idSIButton9");
    private By passwordField = By.id("i0118");
    private By staySignedInText = By.xpath("//*[contains(text(),'Stay signed')]");
    private By yesButton = By.id("idSIButton9");

    // ===== Login Method =====
    public void login(String username, String password) throws InterruptedException {

        // 1. Enter Email
        WebElement email = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
        email.sendKeys(username);

        driver.findElement(nextButton).click();

        // 2. Enter Password
        WebElement pwd = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        pwd.sendKeys(password);

        driver.findElement(nextButton).click();

        // 3. Handle MFA manually
        System.out.println("⚠️ Please enter the authenticator code on the portal manually.");
        System.out.println("Press Enter here after entering the code to continue the test...");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();

        // 4. Handle Stay signed in
        try {
            WebDriverWait waitKmsi = new WebDriverWait(driver, Duration.ofSeconds(30));

            waitKmsi.until(ExpectedConditions.visibilityOfElementLocated(staySignedInText));

            WebElement yesBtn = waitKmsi.until(webDriver -> {
                WebElement btn = webDriver.findElement(yesButton);
                return (btn.isDisplayed() && btn.isEnabled()) ? btn : null;
            });

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'}); arguments[0].click();",
                    yesBtn
            );

            System.out.println("✅ Clicked 'Yes' on Stay signed in page.");

        } catch (Exception e) {
            System.out.println("ℹ️ Stay signed in page not displayed.");
        }

        System.out.println("✅ Login Successful. You can now continue with tests.");
    }
}
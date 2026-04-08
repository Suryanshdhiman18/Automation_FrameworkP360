package pages.common;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import org.slf4j.Logger;
import utils.LoggerUtil;

import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private static final Logger log = LoggerUtil.getLogger(LoginPage.class);

    // ===== Constructor =====
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // ===== Locators =====
    private By emailField = By.id("i0116");
    private By nextButton = By.id("idSIButton9");
    private By passwordField = By.id("i0118");

    // Stay signed in
    private By staySignedInTitle =
            By.xpath("//*[contains(text(),'Stay signed in')]");

    private By yesButton = By.id("idSIButton9");

    // Post login (VERY IMPORTANT)
    private By postLoginElement =
            By.xpath("//span[contains(text(),'Baskets') or contains(text(),'Dashboard')]");

    // ===== Login Method =====
    public void login(String username, String password) {

        log.info("Starting login process");

        try {
            // ---------- Email ----------
            WebElement email = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(emailField));
            email.sendKeys(username);
            log.info("Username entered");

            driver.findElement(nextButton).click();

            // ---------- Password ----------
            WebElement pwd = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(passwordField));
            pwd.sendKeys(password);
            log.info("Password entered");

            driver.findElement(nextButton).click();

            // ---------- MFA Handling (NEW) ----------
            waitForMFACompletion();

            // ---------- Stay Signed In ----------
            handleStaySignedIn();

            log.info("Login completed successfully");

        } catch (Exception e) {
            log.error("Login failed", e);
            throw e;
        }
    }

    // ===== MFA WAIT (NEW CORE FIX) =====
    private void waitForMFACompletion() {

        WebDriverWait waitMFA = new WebDriverWait(driver, Duration.ofSeconds(120));

        log.warn("MFA step detected - waiting for user to complete on UI...");

        try {
            waitMFA.until(ExpectedConditions.or(

                    // ✅ Either dashboard loads
                    ExpectedConditions.visibilityOfElementLocated(postLoginElement),

                    // ✅ Or stay signed in appears
                    ExpectedConditions.visibilityOfElementLocated(staySignedInTitle)
            ));

            log.info("MFA completed successfully (UI transition detected)");

        } catch (TimeoutException e) {
            log.error("MFA not completed within expected time", e);
            throw e;
        }
    }

    // ===== Handle Stay Signed In =====
    private void handleStaySignedIn() {

        WebDriverWait waitKmsi = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            log.info("Checking for 'Stay signed in?' prompt");

            WebElement title = waitKmsi.until(
                    ExpectedConditions.visibilityOfElementLocated(staySignedInTitle));

            if (title.isDisplayed()) {

                log.info("'Stay signed in?' prompt detected");

                WebElement yesBtn = waitKmsi.until(
                        ExpectedConditions.elementToBeClickable(yesButton));

                try {
                    yesBtn.click();
                } catch (Exception e) {
                    ((JavascriptExecutor) driver)
                            .executeScript("arguments[0].click();", yesBtn);
                }

                log.info("Clicked 'Yes' on Stay signed in");

                waitKmsi.until(ExpectedConditions.invisibilityOf(title));

                log.info("'Stay signed in?' handled successfully");
            }

        } catch (TimeoutException e) {
            log.info("'Stay signed in?' prompt not displayed — continuing");
        } catch (Exception e) {
            log.warn("Error while handling 'Stay signed in?'", e);
        }
    }
}
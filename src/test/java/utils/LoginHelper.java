package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Scanner;

public class LoginHelper {

    public static void login(WebDriver driver) throws InterruptedException {
        driver.get(utils.ConfigReader.getProperty("url"));
        driver.manage().window().maximize();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 1. Enter Email
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("i0116")));
        emailField.sendKeys(utils.ConfigReader.getProperty("username"));

        // Click Next
        WebElement nextButton = driver.findElement(By.id("idSIButton9"));
        nextButton.click();

        // 2. Enter Password
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("i0118")));
        passwordField.sendKeys(utils.ConfigReader.getProperty("password"));

        // Click Sign In
        WebElement signInButton = driver.findElement(By.id("idSIButton9"));
        signInButton.click();

        // 3. Handle MFA / Authenticator manually
        System.out.println("⚠️ Please enter the authenticator code on the portal manually.");
        System.out.println("Press Enter here after entering the code to continue the test...");
        Scanner sc = new Scanner(System.in);
        sc.nextLine(); // wait until user presses Enter

        // 4. Handle "Stay signed in?" if it appears
        try {
            WebElement staySignedIn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idSIButton9")));
            staySignedIn.click();
        } catch (Exception e) {
            System.out.println("Stay signed in? prompt not shown.");
        }

        System.out.println("✅ Login Successful. You can now continue with tests.");
    }
}

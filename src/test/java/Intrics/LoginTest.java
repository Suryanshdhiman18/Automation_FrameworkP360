package Intrics;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import utils.ConfigReader;

public class LoginTest {

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            // Load properties
            String appUrl = ConfigReader.getProperty("url");
            String username = ConfigReader.getProperty("username");
            String password = ConfigReader.getProperty("password");

            driver.get(appUrl);
            driver.manage().window().maximize();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // 1. Enter Email
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("i0116")));
            emailField.sendKeys(username);

            // Click Next (email step)
            WebElement nextButton = driver.findElement(By.id("idSIButton9"));
            nextButton.click();

            // 2. Enter Password
            WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("i0118")));
            passwordField.sendKeys(password);

            // Click Sign In (password step)
            WebElement signInButton = driver.findElement(By.id("idSIButton9"));
            signInButton.click();

            // 3. Handle "Stay signed in?" (optional)
            try {
                WebElement staySignedIn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idSIButton9")));
                staySignedIn.click();
            } catch (Exception e) {
                System.out.println("Stay signed in? prompt not shown.");
            }

            System.out.println("Login Successful. Title: " + driver.getTitle());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}

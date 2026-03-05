package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import io.github.bonigarcia.wdm.WebDriverManager;
import pages.common.LoginPage;
import utils.ConfigReader;
import factory.DriverFactory;


public class BaseTest {

    protected WebDriver driver;

    @BeforeClass
    public void setup() throws InterruptedException {
    	
    	System.out.println("Initializing driver...");
        DriverFactory.initDriver("chrome");
        
        driver = DriverFactory.getDriver();
        System.out.println("Driver value: " + driver);
        
        System.out.println("URL from config: " + ConfigReader.get("url"));

        driver.get(ConfigReader.get("url"));

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
                ConfigReader.get("username"),
                ConfigReader.get("password")
        );
    }

    @AfterClass
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
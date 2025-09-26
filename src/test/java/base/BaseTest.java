package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import io.github.bonigarcia.wdm.WebDriverManager;
import utils.LoginHelper;

public class BaseTest {
    protected static WebDriver driver;

    @BeforeClass
    public void setUp() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Login once for all tests in this class
        LoginHelper.login(driver);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
//            driver.quit();
        	System.out.println("Return null");
        }
    }
}

package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import config.ConfigReader;
import pages.common.LoginPage;
import utils.PopupHandler;
import factory.DriverFactory;

// ✅ Logger imports
import org.slf4j.Logger;
import utils.LoggerUtil;

public class BaseTest {

    private WebDriver driver;

    // ✅ Initialize logger
    private static final Logger log = LoggerUtil.getLogger(BaseTest.class);

    @BeforeClass
    public void setup() throws InterruptedException {

        log.info("Initializing WebDriver...");

        DriverFactory.initDriver("chrome");

        setDriver(DriverFactory.getDriver());

        log.info("Driver initialized: {}", getDriver());

        String url = ConfigReader.get("url");
        log.info("Navigating to URL: {}", url);

        getDriver().get(url);

        log.info("Performing login...");

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(
                ConfigReader.get("username"),
                ConfigReader.get("password")
        );

        log.info("Login successful");

        // Optional popup handling
         PopupHandler.handleReleaseNotesPopup(getDriver());

        log.info("Setup completed successfully");
    }

    @AfterClass
    public void tearDown() {

        log.info("Closing driver...");

        DriverFactory.quitDriver();

        log.info("Driver closed successfully");
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }
}
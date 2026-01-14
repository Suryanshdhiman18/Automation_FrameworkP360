package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.GridUtils;
import utils.ScreenshotUtil;

import java.time.Duration;
import java.util.Map;

public class GridPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private GridUtils gridUtils;

    private By gridContainer = By.cssSelector("kendo-grid");

    public GridPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.gridUtils = new GridUtils(driver);
    }

    public void waitForGridToLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(gridContainer));
        ScreenshotUtil.captureScreenshot(driver, "GridLoaded");
    }

    public Map<String, Map<String, String>> getGridData() {
        return gridUtils.extractGridData();
    }
}

package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class GridUtils {

    private WebDriver driver;

    public GridUtils(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Get total rows in grid
     */
    public int getRowCount() {
        return driver.findElements(By.xpath("//div[@class='ag-center-cols-container']//div[contains(@class,'ag-row')]")).size();
    }

    /**
     * Get cell value by row and column
     */
    public String getCellValue(int rowIndex, String colId) {
        String xpath = "(//div[@class='ag-center-cols-container']//div[contains(@class,'ag-row')])[" + rowIndex + "]" +
                       "//div[contains(@col-id,'" + colId + "')]";
        return driver.findElement(By.xpath(xpath)).getText().trim();
    }
}

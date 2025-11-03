package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.*;

public class GridUtils {

    private WebDriver driver;

    public GridUtils(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Get total rows in grid
     */
    public int getRowCount() {
        return driver.findElements(By.cssSelector(".k-grid-content-locked tbody tr")).size();
    }

    /**
     * Extracts all grid data (Kendo Grid)
     * Returns Map of Retailer → {HeaderName → CellValue}
     */
    public Map<String, Map<String, String>> extractGridData() {
        Map<String, Map<String, String>> gridData = new LinkedHashMap<>();

        // --- Step 1: Get all headers ---
        List<WebElement> headerCells = driver.findElements(By.cssSelector(".k-grid-header thead th"));
        List<String> headers = new ArrayList<>();
        for (WebElement header : headerCells) {
            String headerName = header.getText().trim();
            if (!headerName.isEmpty()) headers.add(headerName);
        }

        // --- Step 2: Get rows from locked (Retailer) and scrollable (Price columns) sections ---
        List<WebElement> lockedRows = driver.findElements(By.cssSelector(".k-grid-content-locked tbody tr"));
        List<WebElement> scrollableRows = driver.findElements(By.cssSelector(".k-grid-content.k-virtual-content tbody tr"));

        // --- Step 3: Loop through each row ---
        for (int i = 0; i < lockedRows.size(); i++) {
            String retailer = lockedRows.get(i).findElement(By.cssSelector("td")).getText().trim();
            Map<String, String> rowData = new LinkedHashMap<>();
            rowData.put("Retailer", retailer);

            List<WebElement> priceCells = scrollableRows.get(i).findElements(By.cssSelector("td"));
            for (int j = 0; j < priceCells.size() && j + 1 < headers.size(); j++) {
                String headerName = headers.get(j + 1); // Skip Retailer
                String value = priceCells.get(j).getText().trim();
                rowData.put(headerName, value.isEmpty() ? "N/A" : value);
            }

            gridData.put(retailer, rowData);
        }

        return gridData;
    }
}


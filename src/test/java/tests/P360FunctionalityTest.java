package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.GridPage;
import pages.P360SearchPage;
import pages.ProductDetailPage;
import utils.BrokenLinkValidator;

import java.util.Map;

public class P360FunctionalityTest extends BaseTest {

    @Test(priority = 1)
    public void verifyP360IsLoaded() {
        P360SearchPage searchPage = new P360SearchPage(driver);
        searchPage.verifyP360Loaded();
        System.out.println("✅ P360 module loaded successfully.");
    }

    @Test(priority = 2, dependsOnMethods = "verifyP360IsLoaded")
    public void searchForProduct() {
        P360SearchPage searchPage = new P360SearchPage(driver);
        searchPage.searchProduct("coke");
        searchPage.clickSearch();
        System.out.println("✅ Search executed for product: coke");
    }

    @Test(priority = 3, dependsOnMethods = "searchForProduct")
    public void openProductDetail() {
        ProductDetailPage productDetailPage = new ProductDetailPage(driver);

        productDetailPage.openFirstProductDetail();
        String description = productDetailPage.waitForProductDescription();

        Assert.assertFalse(description.isEmpty(),
                "❌ Product description should not be empty");

        System.out.println("✅ Product Detail opened. Description: " + description);
    }

    @Test(priority = 4, dependsOnMethods = "openProductDetail")
    public void verifyGridDataUI() {
        GridPage gridPage = new GridPage(driver);

        gridPage.waitForGridToLoad();
        Map<String, Map<String, String>> gridData = gridPage.getGridData();

        Assert.assertFalse(gridData.isEmpty(),
                "❌ Grid should contain at least one retailer row");

        System.out.println("✅ Grid UI data verified:");
        gridData.forEach((retailer, data) ->
                System.out.println("🟢 " + retailer + " → " + data)
        );
    }
    
    @Test(priority = 5, dependsOnMethods = "openProductDetail")
    public void exportResults() {
        ProductDetailPage detailPage = new ProductDetailPage(driver);
        detailPage.exportSearchResults();
    }


    
    @Test(priority = 6, dependsOnMethods = "openProductDetail")
    public void checkBrokenLinks() {
    	BrokenLinkValidator validator = new BrokenLinkValidator(driver);
    	validator.validateLinks();
    	validator.validateImages();
    	
    	validator.assertAll();
    	
    	System.out.println("✅ Broken links & images validation completed.");
    }
    
}


// -- Mixed Code

//package tests;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.openqa.selenium.By;
//import org.openqa.selenium.ElementClickInterceptedException;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.Keys;
//import org.openqa.selenium.StaleElementReferenceException;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.testng.Assert;
//import org.testng.annotations.Test;
//import base.BaseTest;
//import io.restassured.RestAssured;
//import io.restassured.response.Response;
//import utils.GridUtils;
//import utils.ScreenshotUtil;
//
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.time.Duration;
//import java.util.ArrayList;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Scanner;
//import java.util.concurrent.TimeoutException;
//
//
//import utils.AuthUtils;
//
////import io.restassured.RestAssured;
////import io.restassured.response.Response;
//
//public class P360FunctionalityTest extends BaseTest {
//
//    WebDriverWait wait;
//
//    @Test(priority = 1)
//    public void verifyP360IsLoaded() {
//        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//
//        // Wait for the search box as a sign that P360 page is loaded
//        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//input[contains(@placeholder,'Digiorno') and contains(@class,'k-input-inner')]")
//        ));
//
//        Assert.assertTrue(searchInput.isDisplayed(), "❌ P360 did not load properly!");
//        System.out.println("✅ P360 module is already loaded.");
//    }
//
//    @Test(priority = 2, dependsOnMethods = "verifyP360IsLoaded")
//    public void typeCokeInSearch() {
//        WebElement searchInput = driver.findElement(
//                By.xpath("//input[contains(@placeholder,'Digiorno') and contains(@class,'k-input-inner')]")
//        );
//
//        searchInput.sendKeys("coke");
//        searchInput.sendKeys(Keys.ENTER);
//        System.out.println("✅ Typed 'coke' in search input.");
//    }
//
//    @Test(priority = 3, dependsOnMethods = "typeCokeInSearch")
//    public void clickSearchButton() {
//        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//span[normalize-space()='Search']")));
//        searchButton.click();
//
//        System.out.println("✅ Search button clicked for 'coke'.");
//    }
//    
//    
//    @Test(priority = 4, dependsOnMethods = "clickSearchButton")
//    public void openProductDetail() {
//        try {
//            // Retry block to handle stale elements
//            int attempts = 0;
//            boolean clicked = false;
//
//            while (attempts < 3 && !clicked) {
//                try {
//                    // Locate the 'View Detail' link right before clicking
//                    WebElement viewDetailLink = wait.until(ExpectedConditions.elementToBeClickable(
//                            By.xpath("//div[@class='filter-block-content']//div[1]//div[1]//div[4]//a[1]")));
//                    viewDetailLink.click();
//                    clicked = true;
//                    System.out.println("✅ Clicked on 'View Detail' for first product.");
//                } catch (StaleElementReferenceException e) {
//                    attempts++;
//                    System.out.println("⚠️ Stale element detected, retrying... Attempt #" + attempts);
//                }
//            }
//
//            // Wait for product description to be visible
//            WebElement productDescription = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                    By.xpath("//span[@class='product-description']")));
//
//            Assert.assertTrue(productDescription.isDisplayed(),
//                    "❌ Product Detail page did not load properly (Product Description missing).");
//
//            // Take screenshot after loading product details
//            ScreenshotUtil.captureScreenshot(driver, "ProductDetail");
//
//            System.out.println("✅ Product Detail page loaded successfully. Description: " 
//                               + productDescription.getText());
//        } catch (Exception e) {
//            ScreenshotUtil.captureScreenshot(driver, "ProductDetail_Error");
//            Assert.fail("❌ Failed to open product detail: " + e.getMessage());
//        }
//    }
//
//    @Test(priority = 5, dependsOnMethods = "openProductDetail")
//    public void exportSearchResults() {
//        try {
//            // Wait for any toast messages or overlays to disappear
//            try {
//                wait.until(ExpectedConditions.invisibilityOfElementLocated(
//                        By.xpath("//span[contains(@class,'message')]")));
//            } catch (Exception e) {
//                System.out.println("⚠️ No toast message blocking the Export button.");
//            }
//
//            // Locate Export button
//            WebElement exportButton = wait.until(ExpectedConditions.presenceOfElementLocated(
//                    By.xpath("//div[@class='p360-top-bar']//button[2]")));
//
//            // Scroll into view in case it's hidden behind header
//            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", exportButton);
//            Thread.sleep(500); // small wait to stabilize view
//
//            // Try clicking — fallback to JS click if intercepted
//            try {
//                wait.until(ExpectedConditions.elementToBeClickable(exportButton)).click();
//                System.out.println("✅ Export button clicked normally.");
//            } catch (ElementClickInterceptedException e) {
//                System.out.println("⚠️ Click intercepted. Retrying with JS click...");
//                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", exportButton);
//                System.out.println("✅ Export button clicked via JavaScript.");
//            }
//
//            // Take a screenshot after successful click
//            ScreenshotUtil.captureScreenshot(driver, "ExportSearchResults");
//
//        } catch (Exception e) {
//            Assert.fail("❌ Unexpected error during export: " + e.getMessage());
//        }
//    }
//
//    
//    @Test(priority = 6, dependsOnMethods = "openProductDetail")
//    public void readGridData() {
//        // Wait for the grid (table) container to load completely
//        WebElement tableContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//div[@class='table-allignment']")));
//        System.out.println("✅ Table container loaded successfully.");
//
//        // Fetch all retailer cells dynamically (works for 1 or many)
//        List<WebElement> retailerCells = driver.findElements(
//                By.xpath("//div[@class='table-allignment']//table//td[contains(text(),'Retailer') or normalize-space(text())!='']"));
//
//        // Fetch all price cells dynamically
//        List<WebElement> priceCells = driver.findElements(
//                By.xpath("//div[@class='table-allignment']//table//td[contains(text(),'$') or contains(@class,'price')]"));
//
//        // Validation — check if table contains at least one retailer
//        if (retailerCells.isEmpty()) {
//            System.out.println("⚠️ No retailer data found for this product.");
//            ScreenshotUtil.captureScreenshot(driver, "GridData_NoRetailers");
//            Assert.fail("❌ Product has no retailer data in the grid.");
//            return;
//        }
//
//        System.out.println("🟩 Retailers found: " + retailerCells.size());
//        System.out.println("🟩 Prices found: " + priceCells.size());
//
//        // Print all available rows (handles 1 or multiple rows safely)
//        for (int i = 0; i < retailerCells.size(); i++) {
//            String retailer = retailerCells.get(i).getText().trim();
//            String price = (i < priceCells.size()) ? priceCells.get(i).getText().trim() : "N/A";
//
//            System.out.println("🟢 Row " + (i + 1) + " → Retailer: " + retailer + " | Price: " + price);
//        }
//
//        // Take screenshot of the full grid
//        ScreenshotUtil.captureScreenshot(driver, "GridData");
//
//        // Final assertion: at least 1 retailer must be displayed
//        Assert.assertTrue(!retailerCells.isEmpty(), "❌ No data rows loaded in grid.");
//    }
//
//    @Test(priority = 7, dependsOnMethods = "readGridData")
//    public void verifyGridDataWithUI() {
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//
//        // Wait for the Kendo grid to appear
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("kendo-grid")));
//        System.out.println("✅ Table container loaded successfully.");
//
//        // --- Step 1: Get all header names ---
//        List<WebElement> headerCells = driver.findElements(By.cssSelector(".k-grid-header thead th"));
//        List<String> headers = new ArrayList<>();
//
//        for (WebElement header : headerCells) {
//            String headerName = header.getText().trim();
//            if (!headerName.isEmpty()) {
//                headers.add(headerName);
//            }
//        }
//
//        System.out.println("🟦 Headers found: " + headers);
//
//        // --- Step 2: Get all rows from both sections ---
//        List<WebElement> lockedRows = driver.findElements(By.cssSelector(".k-grid-content-locked tbody tr"));
//        List<WebElement> scrollableRows = driver.findElements(By.cssSelector(".k-grid-content.k-virtual-content tbody tr"));
//
//        Assert.assertTrue(!lockedRows.isEmpty(), "❌ No grid rows found!");
//        System.out.println("🟩 Total rows found: " + lockedRows.size());
//
//        // --- Step 3: Loop through each row ---
//        for (int i = 0; i < lockedRows.size(); i++) {
//            Map<String, String> rowData = new LinkedHashMap<>();
//
//            // Retailer cell (locked)
//            String retailer = lockedRows.get(i).findElement(By.cssSelector("td")).getText().trim();
//            rowData.put("Retailer", retailer);
//
//            // All other price columns (scrollable section)
//            List<WebElement> priceCells = scrollableRows.get(i).findElements(By.cssSelector("td"));
//            for (int j = 0; j < priceCells.size() && j + 1 < headers.size(); j++) { // +1 offset for Retailer column
//                String headerName = headers.get(j + 1); // Skip Retailer
//                String cellValue = priceCells.get(j).getText().trim();
//                rowData.put(headerName, cellValue.isEmpty() ? "N/A" : cellValue);
//            }
//
//            // --- Print row data neatly ---
//            System.out.println("🟢 Row " + (i + 1) + " Data: " + rowData);
//        }
//
//    }
//
//
//
//    
//    @Test(priority = 8, dependsOnMethods = "openProductDetail")
//    public void checkBrokenLinksAndImages() {
//        System.out.println("🔍 Checking broken links...");
//
//        List<WebElement> links = driver.findElements(By.tagName("a"));
//        for (WebElement link : links) {
//            String url = link.getAttribute("href");
//            if (url == null || url.isEmpty() || url.startsWith("javascript")) continue;
//
//            try {
//                HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
//                conn.setRequestMethod("HEAD");
//                conn.connect();
//                int status = conn.getResponseCode();
//
//                if (status >= 400) {
//                    System.out.println("❌ Broken Link: " + url + " → " + status);
//                } else {
//                    System.out.println("✅ Valid Link: " + url + " → " + status);
//                }
//
//            } catch (Exception e) {
//                System.out.println("❌ Exception on Link: " + url + " → " + e.getMessage());
//            }
//        }
//
//        System.out.println("🔍 Checking broken images...");
//
//        List<WebElement> images = driver.findElements(By.tagName("img"));
//        for (WebElement img : images) {
//            String src = img.getAttribute("src");
//            if (src == null || src.isEmpty()) continue;
//
//            try {
//                HttpURLConnection conn = (HttpURLConnection) new URL(src).openConnection();
//                conn.setRequestMethod("HEAD");
//                conn.connect();
//                int status = conn.getResponseCode();
//
//                if (status >= 400) {
//                    System.out.println("❌ Broken Image: " + src + " → " + status);
//                } else {
//                    System.out.println("🖼️ Valid Image: " + src + " → " + status);
//                }
//
//            } catch (Exception e) {
//                System.out.println("❌ Exception on Image: " + src + " → " + e.getMessage());
//            }
//        }
//    }
//    
//    @Test(priority = 10, dependsOnMethods = "verifyGridDataWithUI")
//    public void verifyGridDataWithAPI() {
//        try {
//            System.out.println("🔍 Starting API-based grid data verification...");
//
//            // --- Step 1: Get the UPC or SK_DIMUPC from the product detail card ---
//            WebElement upcElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                    By.xpath("//label[normalize-space()='UPC:']/following-sibling::span")));
//            String upc = upcElement.getText().trim();
//            System.out.println("📦 UPC found on Product Detail page: " + upc);
//            
////            GridUtils gridUtils = new GridUtils(driver);
////            Map<String, Map<String, String>> gridData = gridUtils.extractGridData();
//
//
//            // --- Step 2: Hit the LoadProduct360ViewV5 API ---
//            
//            JSONObject requestPayload = new JSONObject();
//            requestPayload.put("Regions", new JSONArray());
//            requestPayload.put("ViewSource", 1);
//            requestPayload.put("IsWeb", true);
//            requestPayload.put("Divisions", new JSONArray());
//            requestPayload.put("Markets", new JSONArray());
//            requestPayload.put("FocusRetailer", "");
//            requestPayload.put("GeoGrouping", "Market");
//            requestPayload.put("SK_DIMUPC", 2204761); // 🔁 TODO: Replace with dynamic SK_DIMUPC if available
//
//             
//            String clientId = "538dbed6-a397-4081-89f5-bf8a2082bcee";
//            String authCode = "1.ARcAOEqNwBthMk-FiVCT_3tpENa-jVOXo4FAifW_iiCCvO5oAUgXAA.AgABBAIAAABlMNzVhAPUTrARzfQjWPtKAwDs_wUA9P-uwUYFKpghdQ8kiEkdyWfuPCHaQexpLbx1q4YD5W88k1ftq4DPuf7GUwmBdAyD7NEO_Psug3fdFysNrM2Yx4NEXWS9Pzmg6q695HyJUTdtgoGEgZTGk7n51I4ts1_GXMlXPkoSzISRAIJWHZ6ka5iqVGkhxyzaz0_KV1zdkwaN1Dt6PEv83vA2vACaCyF-KMPjTalUmfedeU2DLXtIuMjxkVwIvOip25nsXkzkONqZ8hQPKTiHKWr3crwPzN6P47skJgexQ2ZojfvH3P2fs1O95tOVnswgEUKdmLg67P7Ty0jEtgM4jouIT5xH0CZOOTKc9ckQouJ14ddWDS3fChyeLDrDZeqGL2O62twhUCPYkc8AOYBowo9W0BshF8jujX6yKFdD9-MYJ63pEPXdrqkIexMQEx-sMYqyUF_dHazv1Zvyspsb34XDEB28wQ4wc_Ms6-5menLOhkOyE5s0wGpUC3lpcJo6q_8zxZWFegWJLLFjcWFZovXDmINntvLhLBZk8TACLnT_AkQqT52UrLqLHfyEJgTJojV7gAa2FX8noxtb4eYUxnVz7cjYm8RHLm2KNmUvYKRelB7rcP0Q83L4SemCsEOL-yEYK8yP9HgyInC2735puDL-6kqSvuToAncdfaWwRlbeE1kAJV_zQ0CEw4It7mZu7rJo3ANli7aGP9WVJda1jmKhZjJlLMgQw_cXgJpfNqM8_Z3aITWkwD8osb5C5YpFw3AlWLDtfyTf9uuYcm_bUeogseRswJmget3KQ9ODYNNxNw4Gc7V50kRFb8zhJxRbZpQpFPd8OCajuoWJbFIeQ3IteOTN6xbgrVyku06IX3MHcAa7mjN0ceREQV8r86olH0c70i-V5kkenZTLgtIdX667x-78Q0I5QKmyrjf1ADPF5fKuwF7y-QGkdlSwAmIlBGlzurJSqoNTHBynu2776hBt6voFIwu7ihu0btzuZTF5Ylz2DKDfnyw5At7mcQ";
//
//           
//            String bearerToken = AuthUtils.getBearerTokenFromAuthCode(clientId, authCode);
//           
//            
////            String bearerToken = AuthUtils.getBearerToken();
//
//            if (bearerToken == null || bearerToken.isEmpty()) {
//                Assert.fail("❌ Could not fetch Bearer Token from Microsoft API.");
//            }
//
//            Response response = RestAssured.given()
//                    .header("Authorization", "Bearer " + bearerToken)
//                    .header("Content-Type", "application/json")
//                    .body(requestPayload.toString())
//                    .post("https://rdcas-syn-api-1-dev.azurewebsites.net/api/LoadProduct360ViewV5")
//                    .then()
//                    .statusCode(200)
//                    .extract()
//                    .response();
//
//            System.out.println("✅ API call successful!");
//            System.out.println(response.asPrettyString());
//
//            
////            AuthUtils auth = new AuthUtils(driver);
////            String token = auth.getAuthTokenFromBrowser();
////            
////            Response response = RestAssured.given()
////            	    .header("Authorization", "Bearer " + token)
////            	    .header("Content-Type", "application/json")
////            	    .body(requestPayload.toString())
////            	    .post("https://rdcas-syn-api-1-dev.azurewebsites.net/api/LoadProduct360ViewV5")
////            	    .then()
////            	    .statusCode(200)
////            	    .extract()
////            	    .response();
//
//            
//            // --- Step 3: Parse API response ---
//            List<Map<String, Object>> retailers = response.jsonPath().getList("AdditionalRetailers");
//            System.out.println("🟦 API Retailers found: " + retailers.size());
//
//            // --- Step 4: Extract UI grid data ---
//            GridUtils gridUtils = new GridUtils(driver);
//            Map<String, Map<String, String>> gridData = extractGridData();
//            System.out.println("🟩 UI grid data extracted: " + gridData.size() + " rows.");
//
//            // --- Step 5: Compare UI vs API values ---
//            for (Map<String, Object> retailerData : retailers) {
//                String apiRetailer = (String) retailerData.get("RetailerName");
//                Double regMin = retailerData.get("RegPriceCurWeekMin") != null
//                        ? Double.parseDouble(retailerData.get("RegPriceCurWeekMin").toString()) : null;
//                Double regMax = retailerData.get("RegPriceCurWeekMax") != null
//                        ? Double.parseDouble(retailerData.get("RegPriceCurWeekMax").toString()) : null;
//                Double effectivePrice = retailerData.get("EffectivePriceCurWeekMode") != null
//                        ? Double.parseDouble(retailerData.get("EffectivePriceCurWeekMode").toString()) : null;
//                Double regIndex = retailerData.get("RegPriceIndex") != null
//                        ? Double.parseDouble(retailerData.get("RegPriceIndex").toString()) : null;
//
//                System.out.println("🔸 API → Retailer: " + apiRetailer +
//                        " | Regular: " + regMin + "-" + regMax +
//                        " | Effective: " + effectivePrice +
//                        " | Index: " + regIndex);
//
//                if (gridData.containsKey(apiRetailer)) {
//                    Map<String, String> uiRow = gridData.get(apiRetailer);
//                    String uiRegular = uiRow.getOrDefault("Regular Price", "N/A");
//                    String uiEffective = uiRow.getOrDefault("Effective Price", "N/A");
//                    String uiIndex = uiRow.getOrDefault("Index", "N/A");
//
//                    // --- Step 6: Assertions ---
//                    Assert.assertTrue(uiRegular.contains(String.valueOf(Math.round(regMin))),
//                            "❌ Regular Price mismatch for " + apiRetailer);
//                    Assert.assertTrue(uiEffective.contains(String.valueOf(Math.round(effectivePrice))),
//                            "❌ Effective Price mismatch for " + apiRetailer);
//                    Assert.assertTrue(uiIndex.contains(String.valueOf(Math.round(regIndex))),
//                            "❌ Index mismatch for " + apiRetailer);
//
//                    System.out.println("✅ Verified Retailer: " + apiRetailer);
//                } else {
//                    System.out.println("⚠️ Retailer " + apiRetailer + " not found in UI grid.");
//                }
//            }
//
//            System.out.println("🎯 Grid data successfully validated against API.");
//
//        } catch (Exception e) {
//            ScreenshotUtil.captureScreenshot(driver, "VerifyGridDataWithAPI_Error");
//            Assert.fail("❌ Grid vs API verification failed: " + e.getMessage());
//        }
//    }
//
//	private Map<String, Map<String, String>> extractGridData() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
 
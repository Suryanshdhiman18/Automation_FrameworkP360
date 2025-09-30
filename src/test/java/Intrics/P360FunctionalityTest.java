package Intrics;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import base.BaseTest;
import utils.ScreenshotUtil;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.List;

//import io.restassured.RestAssured;
//import io.restassured.response.Response;

public class P360FunctionalityTest extends BaseTest {

    WebDriverWait wait;

    @Test(priority = 1)
    public void verifyP360IsLoaded() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Wait for the search box as a sign that P360 page is loaded
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[contains(@placeholder,'Digiorno') and contains(@class,'k-input-inner')]")
        ));

        Assert.assertTrue(searchInput.isDisplayed(), "❌ P360 did not load properly!");
        System.out.println("✅ P360 module is already loaded.");
    }

    @Test(priority = 2, dependsOnMethods = "verifyP360IsLoaded")
    public void typeCokeInSearch() {
        WebElement searchInput = driver.findElement(
                By.xpath("//input[contains(@placeholder,'Digiorno') and contains(@class,'k-input-inner')]")
        );

        searchInput.sendKeys("coke");
        searchInput.sendKeys(Keys.ENTER);
        System.out.println("✅ Typed 'coke' in search input.");
    }

    @Test(priority = 3, dependsOnMethods = "typeCokeInSearch")
    public void clickSearchButton() {
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[normalize-space()='Search']")));
        searchButton.click();

        System.out.println("✅ Search button clicked for 'coke'.");
    }
    
//    @Test(priority = 4, dependsOnMethods = "clickSearchButton")
//    public void openProductDetail() {
//        // Click on first product's "View Detail" link
//        WebElement viewDetailLink = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//div[@class='filter-block-content']//div[1]//div[1]//div[4]//a[1]")));
//        viewDetailLink.click();
//        System.out.println("✅ Clicked on 'View Detail' for first product.");
//
//        // Validate product description is visible
//        WebElement productDescription = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//span[@class='product-description']")));
//        
//        Assert.assertTrue(productDescription.isDisplayed(),
//                "❌ Product Detail page did not load properly (Product Description missing).");
//
//        System.out.println("✅ Product Detail page loaded successfully. Description: " 
//                           + productDescription.getText());
//    } -- Without Screenshot
    
    @Test(priority = 4, dependsOnMethods = "clickSearchButton")
    public void openProductDetail() {
        WebElement viewDetailLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@class='filter-block-content']//div[1]//div[1]//div[4]//a[1]")));
        viewDetailLink.click();
        System.out.println("✅ Clicked on 'View Detail' for first product.");

        WebElement productDescription = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[@class='product-description']")));

        Assert.assertTrue(productDescription.isDisplayed(),
                "❌ Product Detail page did not load properly (Product Description missing).");

        // Take screenshot after loading product details
        ScreenshotUtil.captureScreenshot(driver, "ProductDetail");

        System.out.println("✅ Product Detail page loaded successfully. Description: " 
                           + productDescription.getText());
    }
    
    
    @Test(priority = 5, dependsOnMethods = "openProductDetail")
    public void exportSearchResults() {
        try {
            // Wait for any toast messages to disappear
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.xpath("//span[contains(@class,'message')]")));
        } catch (Exception e) {
            System.out.println("⚠️ No toast message blocking the Export button.");
        }

        // Now safely click Export button
        WebElement exportButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@class='p360-top-bar']//button[2]")));
        exportButton.click();
        System.out.println("✅ Export button clicked.");
    }
    
    
    @Test(priority = 6, dependsOnMethods = "openProductDetail")
    public void readGridData() {
        // Example: capture the first row Retailer + Price
        WebElement firstRetailerCell = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("(//div[@class='ag-center-cols-container']//div[contains(@class,'ag-row')])[1]//div[contains(@col-id,'Retailer')]")
        ));
        WebElement firstPriceCell = driver.findElement(
                By.xpath("(//div[@class='ag-center-cols-container']//div[contains(@class,'ag-row')])[1]//div[contains(@col-id,'Regular Price')]")
        );

        System.out.println("🟢 UI Grid -> Retailer: " + firstRetailerCell.getText() +
                           " | Price: " + firstPriceCell.getText());
    }

//    @Test(priority = 7, dependsOnMethods = "readGridData")
//    public void verifyGridDataWithAPI() {
//        // Example API call (replace with real productId)
//        Response response = RestAssured
//                .given()
//                .baseUri("https://rdcas-syn-hom-app-1-uat.azurewebsites.net")
//                .when()
//                .get("/price-iq/api/product/12345/details") // 🔴 replace dynamically
//                .then()
//                .statusCode(200)
//                .extract().response();
//
//        String retailer = response.jsonPath().getString("retailer[0].name");
//        String regularPrice = response.jsonPath().getString("retailer[0].regularPrice");
//
//        System.out.println("🟢 API -> Retailer: " + retailer + " | Regular Price: " + regularPrice);
//
//        WebElement firstRetailerCell = driver.findElement(
//                By.xpath("(//div[@class='ag-center-cols-container']//div[contains(@class,'ag-row')])[1]//div[contains(@col-id,'Retailer')]")
//        );
//        WebElement firstPriceCell = driver.findElement(
//                By.xpath("(//div[@class='ag-center-cols-container']//div[contains(@class,'ag-row')])[1]//div[contains(@col-id,'Regular Price')]")
//        );
//
//        Assert.assertEquals(firstRetailerCell.getText(), retailer, "❌ Retailer mismatch!");
//        Assert.assertEquals(firstPriceCell.getText(), regularPrice, "❌ Regular Price mismatch!");
//    }
    
    @Test(priority = 8, dependsOnMethods = "openProductDetail")
    public void checkBrokenLinksAndImages() {
        System.out.println("🔍 Checking broken links...");

        List<WebElement> links = driver.findElements(By.tagName("a"));
        for (WebElement link : links) {
            String url = link.getAttribute("href");
            if (url == null || url.isEmpty() || url.startsWith("javascript")) continue;

            try {
                HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
                conn.setRequestMethod("HEAD");
                conn.connect();
                int status = conn.getResponseCode();

                if (status >= 400) {
                    System.out.println("❌ Broken Link: " + url + " → " + status);
                } else {
                    System.out.println("✅ Valid Link: " + url + " → " + status);
                }

            } catch (Exception e) {
                System.out.println("❌ Exception on Link: " + url + " → " + e.getMessage());
            }
        }

        System.out.println("🔍 Checking broken images...");

        List<WebElement> images = driver.findElements(By.tagName("img"));
        for (WebElement img : images) {
            String src = img.getAttribute("src");
            if (src == null || src.isEmpty()) continue;

            try {
                HttpURLConnection conn = (HttpURLConnection) new URL(src).openConnection();
                conn.setRequestMethod("HEAD");
                conn.connect();
                int status = conn.getResponseCode();

                if (status >= 400) {
                    System.out.println("❌ Broken Image: " + src + " → " + status);
                } else {
                    System.out.println("🖼️ Valid Image: " + src + " → " + status);
                }

            } catch (Exception e) {
                System.out.println("❌ Exception on Image: " + src + " → " + e.getMessage());
            }
        }
    }

    
    
}
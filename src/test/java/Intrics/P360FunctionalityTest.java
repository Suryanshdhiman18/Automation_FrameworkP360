package Intrics;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import base.BaseTest;

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
//        // Click "View Detail" link for first product
//        WebElement viewDetailLink = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//div[@class='filter-block-content']//div[1]//div[1]//div[4]//a[1]")
//        ));
//        viewDetailLink.click();
//
//        System.out.println("✅ Clicked 'View Detail' of first product.");
//    }
    
    @Test(priority = 4, dependsOnMethods = "clickSearchButton")
    public void openProductDetail() {
        // Click on first product's "View Detail" link
        WebElement viewDetailLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@class='filter-block-content']//div[1]//div[1]//div[4]//a[1]")));
        viewDetailLink.click();
        System.out.println("✅ Clicked on 'View Detail' for first product.");

        // Validate product description is visible
        WebElement productDescription = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[@class='product-description']")));
        
        Assert.assertTrue(productDescription.isDisplayed(),
                "❌ Product Detail page did not load properly (Product Description missing).");

        System.out.println("✅ Product Detail page loaded successfully. Description: " 
                           + productDescription.getText());
    }

    
//    @Test(priority = 5, dependsOnMethods = "openProductDetail")
//    public void exportProductDetailData() throws InterruptedException {
//        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//
//        // Wait for the export/download button using class + nested span/icon
//        WebElement exportButton = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//button[contains(@class,'p360-top-bar-actions-button')]//span[contains(@class,'material-symbols-outlined') and text()='download']")
//        ));
//
//        // Click the export button
//        exportButton.click();
//        System.out.println("✅ Export button clicked. Product detail data download initiated.");
//
//        // Wait a few seconds to let the download start
//        Thread.sleep(3000);
//    }

    @Test(priority = 5, dependsOnMethods = "openProductDetail")
    public void exportSearchResults() {
        // Wait until Export button is clickable
        WebElement exportButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@class='p360-top-bar']//button[2]")));
        
        exportButton.click();
        System.out.println("✅ Export button clicked.");

        // (Optional) Add validation if a file is downloaded OR a confirmation toast appears
        // Example: wait for toast message
        try {
            WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(@class,'toast-message')]")));
            System.out.println("✅ Export confirmed with message: " + toast.getText());
        } catch (Exception e) {
            System.out.println("⚠️ Export button clicked but no confirmation toast was found.");
        }
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
}
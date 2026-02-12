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
        searchPage.searchProduct("mango");
        searchPage.clickSearch();
        System.out.println("✅ Search executed for product: mango");
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
    public void shareProductWithUser() {
        ProductDetailPage productDetailPage = new ProductDetailPage(driver);

        productDetailPage.openShareDialog();

        productDetailPage.enterShareDescription("Sharing product for review");
        productDetailPage.selectUserToShare("Shruti"); // 🔁 replace with valid user
        productDetailPage.addOptionalComment("Please review pricing and availability");

        productDetailPage.clickShareConfirm();
        productDetailPage.verifyShareSuccess();

        System.out.println("✅ Product shared successfully with selected user.");
    }



    
    @Test(priority = 7, dependsOnMethods = "openProductDetail")
    public void checkBrokenLinks() {
    	BrokenLinkValidator validator = new BrokenLinkValidator(driver);
    	validator.validateLinks();
    	validator.validateImages();
    	
    	validator.assertAll();
    	
    	System.out.println("✅ Broken links & images validation completed.");
    }
    
}

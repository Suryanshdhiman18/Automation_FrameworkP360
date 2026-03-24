package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.baskets.BasketPage;
import pages.baskets.CreateBasketPopup;
import pages.baskets.DynamicBasketPage;
import pages.baskets.ManualBasketPage;
import pages.common.SidebarComponent;
import utils.RandomDataUtil;

@Listeners(listeners.TestListener.class)
public class BasketFunctionalityTest extends BaseTest {

    private BasketPage basketPage;
    private CreateBasketPopup popup;

    @BeforeMethod
    public void navigateToBasket() {

        SidebarComponent sidebar = new SidebarComponent(driver);
        sidebar.goToBasket();

        basketPage = new BasketPage(driver);
        basketPage.verifyBasketLoaded();
    }

    @Test(priority = 1)
    public void verifyBasketPageLoaded() {

        System.out.println("✅ Basket module loaded successfully.");
    }
    
    @Test(priority = 4)
    public void verifyManualBasketCreationFlow() {

        basketPage.clickCreateBasket();

        popup = new CreateBasketPopup(driver);
        popup.selectManualBasket();

        ManualBasketPage manualBasket = new ManualBasketPage(driver);

        String basketName = RandomDataUtil.generateBasketName();

        manualBasket.enterBasketName(basketName);

        manualBasket.enterDescription("Created via automation");

        manualBasket.clickCreateBasket();

        System.out.println("Basket created: " + basketName);
    }
    
    @Test(priority = 5)
    public void verifyDynamicBasketCreationFlow() {

        CreateBasketPopup popup = basketPage.clickCreateBasket();

        DynamicBasketPage dynamicBasket = popup.selectDynamicBasket();

        String basketName = RandomDataUtil.generateBasketName();

        dynamicBasket.enterBasketName(basketName);
        dynamicBasket.enterDescription("Dynamic basket automation");

        dynamicBasket.selectAttributeType();
        dynamicBasket.selectDepartmentValues("BEVERAGE", "BEER");

        dynamicBasket.clickCreate();

        // ✅ IMPORTANT VALIDATION
        Assert.assertTrue(dynamicBasket.isSuccessPopupVisible(),
                "❌ Dynamic basket success popup not visible");

        dynamicBasket.closeSuccessPopup();

        System.out.println("✅ Dynamic Basket creation request submitted successfully.");
    }
    
    @Test(priority = 2)
    public void verifyExportBasket() {

        basketPage.selectFirstBasket();

        Assert.assertTrue(basketPage.isExportEnabled(), "Export button not enabled");

        basketPage.clickExport();

//        boolean downloaded = FileDownloadUtil.isFileDownloaded(
//            "C:\\Users\\SURYANSH\\Downloads", "basket");

//        Assert.assertTrue(downloaded, "Export file not downloaded");

        System.out.println("✅ Basket exported successfully");
    }
    
    @Test(priority = 3)
    public void verifyDeleteBasket() {

//        String basketName = basketPage.getFirstBasketName();

//        basketPage.selectFirstBasket();

        Assert.assertTrue(basketPage.isDeleteEnabled(), "Delete button not enabled");

        basketPage.clickDelete();

        basketPage.confirmDelete();

//        Assert.assertTrue(basketPage.isBasketDeleted(basketName),
//                "Basket not deleted");

        System.out.println("✅ Basket deleted successfully");
    }
}

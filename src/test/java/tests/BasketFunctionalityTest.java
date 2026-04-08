package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
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
import utils.LoggerUtil;

import org.slf4j.Logger;

@Listeners(listeners.TestListener.class)
public class BasketFunctionalityTest extends BaseTest {

    private BasketPage basketPage;
    private static final Logger log = LoggerUtil.getLogger(BasketFunctionalityTest.class);

    // -------- Navigate Once --------
    @BeforeClass
    public void navigateToBasketModule() {

        log.info("Navigating to Basket module (BeforeClass)");

        SidebarComponent sidebar = new SidebarComponent(getDriver());
        sidebar.goToBasket();
    }

    // -------- Setup Before Each Test --------
    @BeforeMethod
    public void setupPage() {

        log.info("Initializing BasketPage and verifying page load");

        basketPage = new BasketPage(getDriver());
        basketPage.verifyBasketLoaded();
    }

    // -------- Test 1 --------
    @Test(priority = 1)
    public void verifyBasketPageLoaded() {

        log.info("Basket module loaded successfully");
    }

    // -------- Test 2 --------
    @Test(priority = 2)
    public void verifyExportBasket() {

        log.info("Starting Export Basket test");

        ensureBasketExists();

//        basketPage.selectFirstBasket();

        Assert.assertTrue(basketPage.isExportEnabled(),
                "Export button not enabled");

        basketPage.clickExport();

        log.info("Basket exported successfully");
    }

    // -------- Test 3 --------
    @Test(priority = 3)
    public void verifyDeleteBasket() {

        log.info("Starting Delete Basket test");

//        ensureBasketExists();

//        basketPage.selectFirstBasket();

        Assert.assertTrue(basketPage.isDeleteEnabled(),
                "Delete button not enabled");

        basketPage.clickDelete();
        basketPage.confirmDelete();

        log.info("Basket deleted successfully");
    }

    // -------- Test 4 --------
    @Test(priority = 4)
    public void verifyManualBasketCreationFlow() {

        log.info("Starting Manual Basket creation test");

        CreateBasketPopup popup = basketPage.clickCreateBasket();

        ManualBasketPage manualBasket = popup.selectManualBasket();

        String basketName = RandomDataUtil.generateBasketName();

        manualBasket.enterBasketName(basketName);
        manualBasket.enterDescription("Created via automation");
        manualBasket.clickCreateBasket();

        Assert.assertFalse(manualBasket.isDuplicateErrorDisplayed(),
                "Duplicate basket error displayed");

        log.info("Manual basket created successfully: {}", basketName);
    }

    // -------- Test 5 --------
    @Test(priority = 5)
    public void verifyDynamicBasketCreationFlow() {

        log.info("Starting Dynamic Basket creation test");

        CreateBasketPopup popup = basketPage.clickCreateBasket();

        DynamicBasketPage dynamicBasket = popup.selectDynamicBasket();

        String basketName = RandomDataUtil.generateBasketName();

        dynamicBasket.enterBasketName(basketName);
        dynamicBasket.enterDescription("Dynamic basket automation");

        dynamicBasket.selectAttributeType();
        dynamicBasket.selectDepartmentValues("BEVERAGE", "BEER");

        dynamicBasket.clickCreate();

        Assert.assertTrue(dynamicBasket.isSuccessPopupVisible(),
                "Dynamic basket success popup not visible");

        dynamicBasket.closeSuccessPopup();

        log.info("Dynamic basket created successfully: {}", basketName);
    }

    // -------- Helper Method --------
    private void ensureBasketExists() {

        log.info("Ensuring at least one basket exists");

        try {
            basketPage.selectFirstBasket();
            log.info("Basket already exists");

        } catch (Exception e) { 

            log.warn("No basket found. Creating a new one");

            CreateBasketPopup popup = basketPage.clickCreateBasket();

            ManualBasketPage manualBasket = popup.selectManualBasket();

            String basketName = RandomDataUtil.generateBasketName();

            manualBasket.enterBasketName(basketName);
            manualBasket.enterDescription("Auto-created for test");
            manualBasket.clickCreateBasket();

            log.info("Created fallback basket: {}", basketName);
        }
    }
}
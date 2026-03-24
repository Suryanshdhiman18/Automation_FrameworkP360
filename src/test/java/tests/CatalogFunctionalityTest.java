package tests;

import org.testng.Assert;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.catalog.CatalogPage;
import pages.catalog.UploadCatalogModal;
import pages.common.SidebarComponent;
import utils.FileUploadUtil;
import utils.CatalogUploadDataProvider;

@Listeners(listeners.TestListener.class)
public class CatalogFunctionalityTest extends BaseTest {

    private CatalogPage catalogPage;
    private UploadCatalogModal modal;

    @BeforeMethod
    public void navigateToCatalog() {

        SidebarComponent sidebar = new SidebarComponent(driver);
        sidebar.goToCatalog();

        catalogPage = new CatalogPage(driver);
        catalogPage.openUploadCatalogModal();

        modal = new UploadCatalogModal(driver);
    }

    @Test(dataProvider = "catalogUploadData",
          dataProviderClass = CatalogUploadDataProvider.class)
    public void testCatalogUpload(String scenario, String fileName) {

        System.out.println("Running scenario: " + scenario);

        modal.uploadFile(FileUploadUtil.getFilePath(fileName));

        modal.clickUpload();

        switch (scenario) {

            case "VALID":

                Assert.assertTrue(modal.isValidationSuccessful(),
                        "Catalog validation should succeed");

                modal.clickUploadValidRecords();
                break;

            case "INVALID":

                Assert.assertTrue(modal.isUploadFailed(),
                        "Invalid catalog should fail validation");
                break;

            case "PARTIAL":

                Assert.assertTrue(modal.isPartialUpload(),
                        "Partial validation message should appear");

                modal.downloadErrorRecords();

                modal.clickUploadValidRecords();
                break;
        }
    }
}
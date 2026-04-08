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
import utils.LoggerUtil;

import org.slf4j.Logger;

@Listeners(listeners.TestListener.class)
public class CatalogFunctionalityTest extends BaseTest {

    private CatalogPage catalogPage;
    private UploadCatalogModal modal;

    private static final Logger log =
            LoggerUtil.getLogger(CatalogFunctionalityTest.class);

    @BeforeMethod
    public void navigateToCatalog() {

        log.info("Navigating to Catalog module");

        SidebarComponent sidebar = new SidebarComponent(getDriver());
        sidebar.goToCatalog();

        catalogPage = new CatalogPage(getDriver());
        catalogPage.openUploadCatalogModal();

        modal = new UploadCatalogModal(getDriver());

        log.info("Upload Catalog modal opened");
    }

    @Test(dataProvider = "catalogUploadData",
          dataProviderClass = CatalogUploadDataProvider.class)
    public void testCatalogUpload(String scenario, String fileName) {

        log.info("Starting Catalog Upload test | Scenario: {} | File: {}",
                scenario, fileName);

        try {

            // -------- Upload File --------
            String filePath = FileUploadUtil.getFilePath(fileName);
            log.info("Uploading file: {}", filePath);

            modal.uploadFile(filePath);

            modal.clickUpload();
            log.info("Upload button clicked");

            // -------- Scenario Handling --------
            switch (scenario) {

                case "VALID":

                    log.info("Valid scenario - expecting successful validation");

                    Assert.assertTrue(modal.isValidationSuccessful(),
                            "Catalog validation should succeed");

                    modal.clickUploadValidRecords();

                    log.info("Valid records uploaded successfully");
                    break;

                case "PARTIAL": 

                	log.info("Partial scenario - expecting partial validation");

                    Assert.assertTrue(modal.isPartialUpload(),
                            "Partial validation message should appear");

                    modal.downloadErrorRecords();
                    log.info("Error records downloaded");

                    modal.clickUploadValidRecords();
                    log.info("Valid records uploaded from partial file");

                    break;

                case "INVALID":                
                    
                    log.info("Invalid scenario - expecting validation failure");

                    Assert.assertTrue(modal.isUploadFailed(),
                            "Invalid catalog should fail validation");

                    log.info("Invalid file validation failed as expected");
                    
//                    modal.cancelUploadAndConfirm();
                    break;

                default:
                    log.error("Unknown scenario: {}", scenario);
                    throw new IllegalArgumentException("Invalid scenario: " + scenario);
            }

        } catch (Exception e) {

            log.error("Catalog upload test failed | Scenario: {}", scenario, e);
            throw e;
        }
    }
}
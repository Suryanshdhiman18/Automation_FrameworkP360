package tests;

import pages.catalog.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.common.SidebarComponent;

public class CatalogFunctionalityTest extends BaseTest{
	
	@Test(priority = 1)
	public void uploadValidCatalog() {
		
		SidebarComponent sidebar = new SidebarComponent(driver);
		sidebar.goToCatalog();
		
		CatalogPage catalogPage = new CatalogPage(driver);
		catalogPage.clickUploadCatalog();
		
		UploadCatalogModal modal = new UploadCatalogModal(driver);
		modal.uploadFile(System.getProperty("user.dir") + "\"/src/test/resources/testfiles/valid_catalog.xlsx\"");
		
		 Assert.assertTrue(modal.isUploadSuccessful(),
	                "Valid file upload should succeed");
	}
	
	 @Test(priority = 2)
	    public void uploadInvalidCatalog() {

	        SidebarComponent sidebar = new SidebarComponent(driver);
	        sidebar.goToCatalog();

	        CatalogPage catalogPage = new CatalogPage(driver);
	        catalogPage.clickUploadCatalog();

	        UploadCatalogModal modal = new UploadCatalogModal(driver);
	        modal.uploadFile(System.getProperty("user.dir") +
	                "/src/test/resources/testfiles/invalid_catalog.xlsx");

	        Assert.assertTrue(modal.isUploadFailed(),
	                "Invalid file upload should fail");
	    }
}

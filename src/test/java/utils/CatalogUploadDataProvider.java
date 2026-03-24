package utils;

import org.testng.annotations.DataProvider;

public class CatalogUploadDataProvider {

    @DataProvider(name = "catalogUploadData")
    public static Object[][] getCatalogUploadData() {

        return new Object[][] {

                {"VALID", "valid_catalog.xlsx"},
                {"INVALID", "invalid_catalog.xlsx"},
                {"PARTIAL", "partial_valid_catalog.xlsx"}

        };
    }
}
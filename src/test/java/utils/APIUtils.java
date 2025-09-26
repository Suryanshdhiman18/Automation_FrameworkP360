package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class APIUtils {

    private static final String BASE_URL = "https://rdcas-syn-hom-app-1-uat.azurewebsites.net";

    /**
     * Fetch product details from API
     * @param productId product ID to query
     * @return API response object
     */
    public static Response getProductDetails(String productId) {
        return RestAssured
                .given()
                .baseUri(BASE_URL)
                .when()
                .get("/price-iq/api/product/" + productId + "/details")
                .then()
                .statusCode(200)
                .extract().response();
    }

    /**
     * Extract retailer name for given index
     */
    public static String getRetailer(Response response, int index) {
        return response.jsonPath().getString("retailer[" + index + "].name");
    }

    /**
     * Extract regular price for given retailer index
     */
    public static String getRegularPrice(Response response, int index) {
        return response.jsonPath().getString("retailer[" + index + "].regularPrice");
    }

    /**
     * Extract promo price for given retailer index
     */
    public static String getPromoPrice(Response response, int index) {
        return response.jsonPath().getString("retailer[" + index + "].promoPrice");
    }
}

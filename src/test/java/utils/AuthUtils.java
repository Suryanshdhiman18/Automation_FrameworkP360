package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class AuthUtils {

    public static String getBearerTokenFromAuthCode(String clientId, String authCode) {
        String redirectUri = "https://rdcas-syn-hom-app-1-dev.azurewebsites.net";
        String scope = "openid profile offline_access";
        String tokenEndpoint = "https://login.microsoftonline.com/c08d4a38-611b-4f32-8589-5093ff7b6910/oauth2/v2.0/token";

        Response response = RestAssured.given()
            .header("Content-Type", "application/x-www-form-urlencoded")
            .formParam("client_id", clientId)
            .formParam("scope", scope)
            .formParam("code", authCode)
            .formParam("redirect_uri", redirectUri)
            .formParam("grant_type", "authorization_code")
            .post(tokenEndpoint)
            .then()
            .statusCode(200)
            .extract()
            .response();

        // ✅ Extract access token from response
        String accessToken = response.jsonPath().getString("access_token");

        System.out.println("✅ Access Token retrieved successfully!");
        System.out.println("Access Token: " + accessToken);

        return accessToken;
    }
}



// -->  New Approach 

//public class AuthUtils {
//
//    private static String cachedToken;
//    private static long expiryTime;
//
//    public static String getValidBearerToken() {
//        if (cachedToken == null || isExpired()) {
//            cachedToken = fetchNewToken();
//        }
//        return cachedToken;
//    }
//
//    private static String fetchNewToken() {
//        // call Microsoft token API
//        // return access_token
//    }
//
//    private static boolean isExpired() {
//        return System.currentTimeMillis() > expiryTime;
//    }
//}



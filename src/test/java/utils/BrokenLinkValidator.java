package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class BrokenLinkValidator {

    private WebDriver driver;
    private SoftAssert softAssert;

    // 🔑 Define your company domain here
    private static final String COMPANY_DOMAIN = "rdcas-syn-hom-app-1-dev.azurewebsites.net";

    public BrokenLinkValidator(WebDriver driver) {
        this.driver = driver;
        this.softAssert = new SoftAssert();
    }

    public void validateLinks() {
        System.out.println("🔍 Validating company links only...");

        List<WebElement> links = driver.findElements(By.tagName("a"));

        for (WebElement link : links) {
            String url = link.getAttribute("href");

            if (url == null || url.isEmpty() || url.startsWith("javascript")) {
                continue;
            }

            try {
                URL linkUrl = new URL(url);

                // 🚫 Skip external domains
                if (!linkUrl.getHost().contains(COMPANY_DOMAIN)) {
                    continue;
                }

                HttpURLConnection conn = (HttpURLConnection) linkUrl.openConnection();
                conn.setRequestMethod("HEAD");

                int status;

                try {
                    conn.connect();
                    status = conn.getResponseCode();

                    // 🔁 Retry with GET if HEAD not allowed
                    if (status == 405) {
                        conn = (HttpURLConnection) linkUrl.openConnection();
                        conn.setRequestMethod("GET");
                        conn.connect();
                        status = conn.getResponseCode();
                    }

                } catch (Exception e) {
                    // fallback to GET
                    conn = (HttpURLConnection) linkUrl.openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();
                    status = conn.getResponseCode();
                }

                System.out.println("🔗 " + url + " → " + status);
                softAssert.assertTrue(status < 400,
                        "Broken Link: " + url + " | Status: " + status);

            } catch (Exception e) {
                softAssert.fail("Exception for Link: " + url + " | " + e.getMessage());
            }
        }
    }

    public void validateImages() {
        System.out.println("🖼️ Validating company images only...");

        List<WebElement> images = driver.findElements(By.tagName("img"));

        for (WebElement img : images) {
            String src = img.getAttribute("src");

            if (src == null || src.isEmpty()) continue;

            try {
                URL imageUrl = new URL(src);

                // 🚫 Skip external domains (like Microsoft maps)
                if (!imageUrl.getHost().contains(COMPANY_DOMAIN)) {
                    continue;
                }

                HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                conn.setRequestMethod("HEAD");

                int status;

                try {
                    conn.connect();
                    status = conn.getResponseCode();

                    // 🔁 Retry with GET if HEAD fails
                    if (status == 405) {
                        conn = (HttpURLConnection) imageUrl.openConnection();
                        conn.setRequestMethod("GET");
                        conn.connect();
                        status = conn.getResponseCode();
                    }

                } catch (Exception e) {
                    conn = (HttpURLConnection) imageUrl.openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();
                    status = conn.getResponseCode();
                }

                System.out.println("🖼️ " + src + " → " + status);
                softAssert.assertTrue(status < 400,
                        "Broken Image: " + src + " | Status: " + status);

            } catch (Exception e) {
                softAssert.fail("Exception for Image: " + src + " | " + e.getMessage());
            }
        }
    }

    public void assertAll() {
        softAssert.assertAll();
    }
}
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

    public BrokenLinkValidator(WebDriver driver) {
        this.driver = driver;
        this.softAssert = new SoftAssert();
    }

    public void validateLinks() {
        System.out.println("🔍 Validating all links on the page...");

        List<WebElement> links = driver.findElements(By.tagName("a"));

        for (WebElement link : links) {
            String url = link.getAttribute("href");

            if (url == null || url.isEmpty() || url.startsWith("javascript")) {
                continue;
            }

            try {
                HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
                conn.setRequestMethod("HEAD");
                conn.connect();

                int status = conn.getResponseCode();

                System.out.println("🔗 " + url + " → " + status);
                softAssert.assertTrue(status < 400,
                        "Broken Link: " + url + " | Status: " + status);

            } catch (Exception e) {
                softAssert.fail("Exception for Link: " + url + " | " + e.getMessage());
            }
        }
    }

    public void validateImages() {
        System.out.println("🖼️ Validating all images on the page...");

        List<WebElement> images = driver.findElements(By.tagName("img"));

        for (WebElement img : images) {
            String src = img.getAttribute("src");

            if (src == null || src.isEmpty()) continue;

            try {
                HttpURLConnection conn = (HttpURLConnection) new URL(src).openConnection();
                conn.setRequestMethod("HEAD");
                conn.connect();

                int status = conn.getResponseCode();

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

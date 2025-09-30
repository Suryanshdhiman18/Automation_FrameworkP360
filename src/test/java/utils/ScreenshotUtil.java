package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;

public class ScreenshotUtil {

    public static void captureScreenshot(WebDriver driver, String screenshotName) {
        try {
            // Add timestamp for uniqueness
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            
            // Capture screenshot
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            
            // Destination folder (project root /screenshots/)
            String destPath = System.getProperty("user.dir") + "/screenshots/" + screenshotName + "_" + timestamp + ".png";
            
            FileUtils.copyFile(srcFile, new File(destPath));
            System.out.println("📸 Screenshot saved at: " + destPath);
        } catch (IOException e) {
            System.out.println("❌ Failed to save screenshot: " + e.getMessage());
        }
    }
}
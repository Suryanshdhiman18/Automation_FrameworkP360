package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties prop;

    // Static block → load once when class is used
    static {
        try {
            FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
            prop = new Properties();
            prop.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get property by key
    public static String getProperty(String key) {
        return prop.getProperty(key);
    }
}

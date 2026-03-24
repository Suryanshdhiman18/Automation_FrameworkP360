package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class RandomDataUtil {

    // Timestamp based unique value
    public static String getTimestamp() {

        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    // Unique basket name
    public static String generateBasketName() {

        return "AutoBasket_" + getTimestamp();
    }

    // Generic unique name
    public static String generateUniqueName(String prefix) {

        return prefix + "_" + getTimestamp();
    }

    // Random UUID
    public static String generateUUID() {

        return UUID.randomUUID().toString();
    }

    // Random number
    public static int randomNumber(int bound) {

        return (int) (Math.random() * bound);
    }

    // Random email (useful for other modules)
    public static String randomEmail() {

        return "testuser_" + getTimestamp() + "@automation.com";
    }
}
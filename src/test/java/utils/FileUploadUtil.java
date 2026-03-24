package utils;

import java.io.File;

public class FileUploadUtil {

    public static String getFilePath(String fileName) {

        String path = System.getProperty("user.dir")
                + "/src/test/resources/testfiles/" + fileName;

        File file = new File(path);

        if (!file.exists()) {
            throw new RuntimeException("❌ File not found: " + path);
        }

        return file.getAbsolutePath();
    }
}
package utils;

import java.io.File;

public class DownloadUtil {

    private static final String DOWNLOAD_DIR = System.getProperty("user.home") + "\\Downloads";

    public static boolean isFileDownloaded(String fileName) {

        File dir = new File(DOWNLOAD_DIR);
        File[] files = dir.listFiles();

        if (files == null) return false;

        for (File file : files) {
            if (file.getName().contains(fileName)) {
                return true;
            }
        }

        return false;
    }
}
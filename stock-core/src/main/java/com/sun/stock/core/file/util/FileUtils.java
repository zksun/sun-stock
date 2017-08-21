package com.sun.stock.core.file.util;

import java.io.File;

/**
 * Created by zksun on 2017/8/19.
 */
public final class FileUtils {

    public static String getDirectoryName(byte type, int code) {
        String market = "";
        switch ((int) type) {
            case 0:
                market = "sh";
                break;
            case 1:
                market = "sz";
                break;
            default:
                throw new IllegalArgumentException("wrong type");
        }
        return market + code;
    }

    public static String createDirectory(String path, String name) {
        String temp = path + File.separator + name;
        File directory = new File(temp);
        if (directory.exists() && directory.isDirectory()) {
            return temp;
        }
        directory.mkdir();
        return temp;
    }

    public static File filePath(String directory, Long time) {
        return new File(directory + File.separator + time + ".txt");
    }
}

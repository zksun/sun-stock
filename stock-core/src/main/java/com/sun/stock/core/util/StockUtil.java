package com.sun.stock.core.util;

import com.sun.stock.core.algorithm.utils.IOUtil;
import com.sun.stock.core.common.internal.StringUtil;
import com.sun.stock.core.domain.enums.StockType;
import com.sun.stock.core.system.Environment;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by zksun on 16-2-10.
 */
public final class StockUtil {
    public final static String STOCK_DIRECTORY_PATTERN = "(sh|sz)[0-9]{6}";

    public final static String STOCK_FILE_PATTERN = "[0-9]{8}\\.txt";

    public final static String COMMON_DATE_FORMATTER = "yyyyMMdd";

    public static boolean isStockDirectory(File file) {
        if (null == file) {
            throw new NullPointerException();
        }

        if (!file.exists() || !file.isDirectory()) {
            throw new IllegalArgumentException("illegal directory");
        }

        return isStockDirectoryName(file.getName());
    }

    public static boolean isStockDirectoryName(String name) {
        if (StringUtils.isBlank(name)) {
            throw new NullPointerException();
        }
        return name.matches(STOCK_DIRECTORY_PATTERN);
    }

    public static String[] getStockCode(String name) {
        String code = name.substring(2, name.length());
        if (isStockDirectoryName(name)) {
            if (name.startsWith("sh")) {
                return new String[]{"sh", code};
            } else {
                return new String[]{"sz", code};
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static boolean isStockDataFile(File file) {
        if (null == file) {
            throw new NullPointerException();
        }
        if (!file.exists() || file.isDirectory()) {
            throw new IllegalArgumentException("illegal file");
        }

        return file.getName().matches(STOCK_FILE_PATTERN);
    }

    public static List<File> getDataFiles(File directory) {
        List<File> result = null;
        if (isStockDirectory(directory)) {
            List<File> list = IOUtil.getFilesByDirectory(directory);
            if (null == list || list.isEmpty()) {
                return result;
            }
            result = new ArrayList<File>();
            for (File file : list) {
                if (isStockDataFile(file)) {
                    result.add(file);
                }
            }
        }

        return result;
    }

    private static String getStockDataDirectoryName(StockType type, String stockCode) {
        switch (type) {
            case SHANGHAI:
                return Environment.SHANGHAI_STOCK_PREFIX + stockCode.trim();
            case SHENZHEN:
                return Environment.SHENZHEN_STOCK_PREFIX + stockCode.trim();
            default:
                return null;
        }
    }


    public static List<File> getAllStockDataFiles(StockType type, String stockCode) {
        if (StringUtil.isBlank(stockCode) || null == type) {
            throw new NullPointerException();
        }
        File directory = new File(Environment.STOCK_ROOT_PATH + getStockDataDirectoryName(type, stockCode));
        if (isStockDirectory(directory)) {
            if (directory.listFiles().length > 0) {
                List<File> list = new ArrayList<>();
                for (int i = 0; i < directory.listFiles().length; i++) {
                    File dataFile = directory.listFiles()[i];
                    if (isStockDataFile(dataFile)) {
                        list.add(dataFile);
                    }
                }
                return list;
            }
        }
        return Collections.EMPTY_LIST;
    }

    public static List<String> getAllStockDirectoryNames(String rootPath) {
        if (StringUtils.isBlank(rootPath)) {
            throw new NullPointerException("rootPath");
        }
        //File directory = new File(Environment.STOCK_ROOT_PATH);
        File directory = new File(rootPath);
        File[] allStockDirectory = directory.listFiles();
        if (allStockDirectory.length > 0) {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < allStockDirectory.length; i++) {
                list.add(allStockDirectory[i].getName().trim());
            }
            return list;
        }
        return Collections.emptyList();
    }

    public static File getStockDataFile(StockType type, String stockCode, Date date, String rootPath) {
        if (StringUtil.isBlank(stockCode) || null == type || null == date || StringUtils.isBlank(rootPath)) {
            throw new NullPointerException();
        }
        DateFormat dateFormat = new SimpleDateFormat(COMMON_DATE_FORMATTER);
        String dateString = dateFormat.format(date);
        File directory = new File(rootPath + getStockDataDirectoryName(type, stockCode));
        if (isStockDirectory(directory)) {
            File dataFile = new File(directory.getPath() + File.separator + dateString + Environment.STOCK_DATA_FILE_SUFFIX);
            if (isStockDataFile(dataFile)) {
                return dataFile;
            }
        }
        return null;
    }

    public static File getStockDataFile(StockType type, String stockCode, String date, String rootPath) throws ParseException {
        if (StringUtil.isBlank(stockCode) || StringUtil.isBlank(date) || StringUtils.isBlank(rootPath)) {
            throw new NullPointerException();
        }

        DateFormat dateFormat = new SimpleDateFormat(COMMON_DATE_FORMATTER);
        Date parse = dateFormat.parse(date);
        return getStockDataFile(type, stockCode, parse, rootPath);
    }

}

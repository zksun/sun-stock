package com.sun.stock.core.test;

import com.sun.stock.core.algorithm.IOUtil;
import com.sun.stock.core.domain.StockTradeDetail;
import com.sun.stock.core.domain.enums.StockType;
import com.sun.stock.core.system.Environment;
import com.sun.stock.core.util.StockUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by zksun on 16-2-10.
 */
public class ReadStockTest {
    @Test
    public void testGetStockData() {
        List<File> directories = IOUtil.getFilesByPath(Environment.STOCK_ROOT_PATH);
        Assert.assertTrue(!directories.isEmpty());
        for (File directory : directories) {
            try {
                System.out.println(StockUtil.isStockDirectory(directory));
            } catch (Exception e) {
                //ignore
            }
        }
    }

    @Test
    public void testStockFilePattern() {
        String fileName = "20140103.txt";

        System.out.println(fileName.matches(StockUtil.STOCK_FILE_PATTERN));
    }

    @Test
    public void testGetStockDataFile() {
        try {
            File stockDataFile = StockUtil.getStockDataFile(StockType.SHANGHAI, "601186", "20140102", Environment.STOCK_ROOT_PATH);
            Assert.assertTrue(stockDataFile.exists());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void testDealStockDataFileLine() {
//        try {
//            File stockDataFile = StockUtil.getStockDataFile(StockType.SHANGHAI, "601186", "20160102", Environment.STOCK_ROOT_PATH);
//            IOUtil.getFileByLine(stockDataFile, new IOReadLineTestHandler());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }

//    @Test
//    public void testDealStockDataFileBytes() {
//        try {
//            File stockDataFile = StockUtil.getStockDataFile(StockType.SHENZHEN, "002024", "20151231", Environment.STOCK_ROOT_PATH);
//            IOUtil.getDataFromFileByBuffer(stockDataFile, new IOGetBytesTestHandler("002024"), 16);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    @Test
//    public void testDealAllStockDataFilesBytes() {
//        List<File> stockDataFiles = StockUtil.getAllStockDataFiles(StockType.SHANGHAI, "600718");
//        if (CollectionUtils.isNotEmpty(stockDataFiles)) {
//            IOGetBytesTestHandler ioGetBytesTestHandler = new IOGetBytesTestHandler("600718");
//            for (File stockDataFile : stockDataFiles) {
//                try {
//                    ioGetBytesTestHandler.setDateString(stockDataFile.getName().split("\\.")[0]);
//                    IOUtil.getDataFromFileByBuffer(stockDataFile, ioGetBytesTestHandler, 16);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//            List<Map.Entry<Long, Long>> entries = ioGetBytesTestHandler.sortMap();
//            System.out.println(entries.size());
//        }
//    }

//    @Test
//    public void testDealHuShen300LotsStockDataFileBytes() {
//        List<String> allStockDirectoryNames = StockUtil.getAllStockDirectoryNames(Environment.STOCK_ROOT_PATH);
//        if (CollectionUtils.isNotEmpty(allStockDirectoryNames)) {
//            List<StockEntry> list = new ArrayList<>(allStockDirectoryNames.size());
//            int i = 0;
//            for (String name : allStockDirectoryNames) {
//                if (StockUtil.isStockDirectoryName(name)) {
//                    String[] stockCode = StockUtil.getStockCode(name);
//                    List<File> stockDataFiles = StockUtil.getAllStockDataFiles(StockType.getTypeByDesc(stockCode[0]), stockCode[1]);
//                    if (CollectionUtils.isNotEmpty(stockDataFiles)) {
//                        IOGetBytesTestHandler ioGetBytesTestHandler = new IOGetBytesTestHandler(stockCode[1]);
//                        for (File stockDataFile : stockDataFiles) {
//                            try {
//                                ioGetBytesTestHandler.setDateString(stockDataFile.getName().split("\\.")[0]);
//                                IOUtil.getDataFromFileByBuffer(stockDataFile, ioGetBytesTestHandler, 16);
//                            } catch (FileNotFoundException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        StockEntry stockEntry = new StockEntry();
//                        stockEntry.dataCache = ioGetBytesTestHandler.getDataCache();
//                        stockEntry.sortMap = ioGetBytesTestHandler.sortMap();
//                        stockEntry.stockTradeDataCache = ioGetBytesTestHandler.getStockTradeDataCache();
//                        list.add(stockEntry);
//                    }
//                }
//                try {
//                    Thread.sleep(100L);
//                } catch (InterruptedException e) {
//                    //ignore
//                }
//                i++;
//                System.out.println("check stock: " + i + "name: " + name);
//            }
//            List<StockEntry> list1 = sortStocks(list);
//            System.out.println("end");
//        }
//
//    }

    static class StockEntry {
        List<Map.Entry<Long, Long>> sortMap;

        Map<Long, Long> dataCache;

        Map<Long, List<StockTradeDetail>> stockTradeDataCache;
    }

    public static List<StockEntry> sortStocks(List<StockEntry> list) {
        Collections.sort(list, new Comparator<StockEntry>() {
            @Override
            public int compare(StockEntry o1, StockEntry o2) {
                long o1long = 0;
                if (null != o1.sortMap && CollectionUtils.isNotEmpty(o1.sortMap)) {
                    o1long = o1.sortMap.get(0).getValue() * o1.sortMap.get(0).getKey();
                }

                long o2long = 0;
                if (null != o2.sortMap && CollectionUtils.isNotEmpty(o2.sortMap)) {
                    o2long = o2.sortMap.get(0).getValue() * o2.sortMap.get(0).getKey();
                }

                if (o1long > o2long) return -1;
                if (o1long < o2long) return 1;
                return 0;
            }
        });
        return list;
    }

}
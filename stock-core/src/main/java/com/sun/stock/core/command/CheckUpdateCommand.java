package com.sun.stock.core.command;

import com.sun.stock.core.file.util.FileUtils;
import com.sun.stock.core.util.StockUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhikunsun on 2017/11/5.
 */
public class CheckUpdateCommand implements Callable<List<String>> {

    @Override
    public List<String> call() throws Exception {
        List<String> maybeUnUpdateList = new ArrayList<>();
        List<String> allStockDirectoryNames = StockUtil.getAllStockDirectoryNames("/Users/zhikunsun/Documents/stock_data");
        for (String directory : allStockDirectoryNames) {
            String d = FileUtils.createDirectory("/Users/zhikunsun/Documents/stock_data", directory);
            File file = FileUtils.filePath(d, 20171103L);
            if (!file.exists()) {
                maybeUnUpdateList.add(d);
            }
        }
        return maybeUnUpdateList;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = null;
        try {
            executorService = Executors.newSingleThreadExecutor();
            List<String> strings = executorService.submit(new CheckUpdateCommand()).get();
            System.out.println(strings.size());
        } finally {
            if (null != executorService) {
                executorService.shutdown();
            }
        }
    }
}

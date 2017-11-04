package com.sun.stock.core.test;

import com.sun.stock.core.file.FileDownloadServer;

/**
 * Created by zhikunsun on 2017/11/4.
 */
public class FileDownloadServerTest {
    public static void main(String[] args) {
        new FileDownloadServer().bind(Integer.valueOf("3128"), "/Users/zhikunsun/Documents/stock_data");
    }
}

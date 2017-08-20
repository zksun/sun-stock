package com.sun.stock.core.test;

import org.apache.commons.io.filefilter.IOFileFilter;
import org.junit.Test;

import java.io.File;
import java.util.Iterator;

/**
 * Created by zksun on 12/05/2017.
 */
public class FileUtils {

    @Test
    public void readStockFile() {

        Iterator<File> fileIterator = org.apache.commons.io.FileUtils.iterateFiles(new File("/Users/hanshou/Downloads/sh600352"), new IOFileFilter() {
            @Override
            public boolean accept(File file) {
                TestLog.getLogger().info(file.getName());
                return true;
            }

            @Override
            public boolean accept(File dir, String name) {
                return false;
            }
        }, null);

        if (null != fileIterator) {
            while (fileIterator.hasNext()) {
                File next = fileIterator.next();
                TestLog.getLogger().info(next.toString());
            }
        }
        TestLog.getLogger().info("iterator end");
    }
}

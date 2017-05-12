package com.sun.stock.core.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zksun on 6/5/16.
 */
public class LogTest {

    private final static Logger logger = LoggerFactory.getLogger("LogTest");

    public static void main(String[] args) {
        logger.info("hello world");
    }

}

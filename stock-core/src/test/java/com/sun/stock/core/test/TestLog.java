package com.sun.stock.core.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zksun on 12/05/2017.
 */
public class TestLog {
    private final static Logger logger = LoggerFactory.getLogger("sun.stock.log.test");

    public static Logger getLogger() {
        return logger;
    }
}

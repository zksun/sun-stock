package com.sun.stock.core.test;

import com.sun.stock.core.common.IOHandler;

/**
 * Created by zksun on 16-2-10.
 */
public class IOReadLineTestHandler implements IOHandler<String> {
    @Override
    public void execute(String value) {
        System.out.println(value);
    }

}

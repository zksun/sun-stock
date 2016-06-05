package com.sun.stock.test;

import com.sun.stock.common.IOHandler;

/**
 * Created by zksun on 16-2-10.
 */
public class IOReadLineTestHandler implements IOHandler<String> {
    @Override
    public void execute(String value) {
        System.out.println(value);
    }

}

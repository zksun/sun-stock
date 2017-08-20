package com.sun.stock.core.common.util;

import org.junit.Test;

import java.time.LocalDate;

/**
 * Created by zksun on 2017/8/19.
 */
public class DateUtilsTest {

    @Test
    public void testGetLocalDate(){
        LocalDate localDateYYYYMMDD = DateUtils.getLocalDateYYYYMMDD(20170809L);
        System.out.println(localDateYYYYMMDD);
    }
}

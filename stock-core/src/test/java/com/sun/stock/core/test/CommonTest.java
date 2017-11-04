package com.sun.stock.core.test;

import org.junit.Test;

import java.time.LocalDate;

/**
 * Created by zhikunsun on 2017/11/5.
 */
public class CommonTest {
    @Test
    public void todayTest() {
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate.getDayOfWeek().toString());
        System.out.println(localDate.getMonth().toString());

        System.out.println(localDate.plusDays(1));

    }
}

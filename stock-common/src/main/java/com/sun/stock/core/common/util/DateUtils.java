package com.sun.stock.core.common.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

/**
 * Created by hanshou on 2017/8/19.
 */
public final class DateUtils {

    public static LocalDate getLocalDateYYYYMMDD(Long time) {
        if (null == time) {
            throw new NullPointerException();
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return LocalDate.parse(time.toString(), dateTimeFormatter);
    }

    public static Long getLocalDateYYYYMMDD(LocalDate time) {
        if (null == time) {
            throw new NullPointerException();
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
//        Integer year = time.getYear();
//        Integer monthValue = time.getMonthValue();
//        Integer dayOfMonth = time.getDayOfMonth();
//
//        StringBuilder stringBuilder = new StringBuilder(year).append(monthValue).append(dayOfMonth);
        String format = time.format(dateTimeFormatter);
        return Long.valueOf(format);
    }

    public static void main(String[] args) {
        System.out.println(getLocalDateYYYYMMDD(LocalDate.now()));
    }

    private DateUtils() {
    }
}

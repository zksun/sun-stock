package com.sun.stock.core.common.util;

import java.time.LocalDate;
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

    private DateUtils() {
    }
}

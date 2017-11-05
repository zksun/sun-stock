package com.sun.stock.core.command;

import com.sun.stock.core.common.util.DateUtils;
import com.sun.stock.core.file.FileDO;
import io.netty.channel.Channel;

import java.time.LocalDate;
import java.util.concurrent.Callable;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

/**
 * Created by zksun on 2017/11/5.
 */
public class UpdateStockCommand implements Callable<Boolean> {

    private final LocalDate start;
    private final Integer stockCode;
    private final byte type;
    private final Channel channel;

    public UpdateStockCommand(LocalDate start, Integer stockCode, byte type, Channel channel) {
        this.start = start;
        this.stockCode = stockCode;
        this.type = type;
        this.channel = channel;
    }

    @Override
    public Boolean call() throws Exception {
        LocalDate localDate = this.start;
        while (!localDate.isAfter(LocalDate.now())) {

            if (localDate.getDayOfWeek().equals(SATURDAY) || localDate.getDayOfWeek().equals(SUNDAY)) {
                localDate = localDate.plusDays(1);
                continue;
            }

            FileDO fileDO = new FileDO();
            fileDO.setTime(DateUtils.getLocalDateYYYYMMDD(localDate));
            fileDO.setCode(stockCode);
            fileDO.setType(type);
            fileDO.setLength((byte) 0);

            channel.writeAndFlush(fileDO);
            localDate = localDate.plusDays(1);
        }

        return true;
    }
}

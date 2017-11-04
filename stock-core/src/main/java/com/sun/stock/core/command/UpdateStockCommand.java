package com.sun.stock.core.command;

import io.netty.channel.Channel;

import java.time.LocalDate;
import java.util.concurrent.Callable;

/**
 * Created by zksun on 2017/11/5.
 */
public class UpdateStockCommand implements Callable<Boolean> {

    private final LocalDate start;
    private final Long stockCode;
    private final byte type;
    private final Channel channel;
    private final String path;

    public UpdateStockCommand(LocalDate start, Long stockCode, byte type, Channel channel, String path) {
        this.start = start;
        this.stockCode = stockCode;
        this.type = type;
        this.channel = channel;
        this.path = path;
    }

    @Override
    public Boolean call() throws Exception {
        return false;
    }
}

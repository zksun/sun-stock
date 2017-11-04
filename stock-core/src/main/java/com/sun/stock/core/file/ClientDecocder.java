package com.sun.stock.core.file;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteOrder;

/**
 * Created by zhikunsun on 2017/11/4.
 */
public class ClientDecocder extends LengthFieldBasedFrameDecoder {

    public ClientDecocder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(ByteOrder.LITTLE_ENDIAN, maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
    }





}

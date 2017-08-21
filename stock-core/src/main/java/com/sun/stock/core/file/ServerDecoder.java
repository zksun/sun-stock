package com.sun.stock.core.file;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteOrder;

/**
 * Created by zksun on 2017/8/19.
 */
public class ServerDecoder extends LengthFieldBasedFrameDecoder {
    private final static int HEAD_SIZE = 13 + 4;

    public ServerDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(ByteOrder.LITTLE_ENDIAN, maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        in = (ByteBuf) super.decode(ctx, in);
        if (null == in) return null;
        if (in.readableBytes() < HEAD_SIZE) {
            throw new RuntimeException("data low than the head size");
        }

        byte type = in.readByte();
        int code = in.readInt();
        long time = in.readLong();
        int length = in.readInt();

        if (in.readableBytes() < length) {
            throw new RuntimeException("data low than the protocol length ");
        }

        FileDO fileDO = new FileDO();
        fileDO.setType(type);
        fileDO.setCode(code);
        fileDO.setTime(time);
        fileDO.setLength(length);
        ByteBuf byteBuf = in.readBytes(length);
        byte[] buf = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(buf);
        fileDO.setDocument(buf);
        return fileDO;
    }
}

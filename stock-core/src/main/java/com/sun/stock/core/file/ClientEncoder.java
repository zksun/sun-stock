package com.sun.stock.core.file;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by zksun on 2017/8/19.
 */
public class ClientEncoder extends MessageToByteEncoder<FileDO> {

    @Override
    protected void encode(ChannelHandlerContext ctx, FileDO msg, ByteBuf out) throws Exception {
        out.writeByte(msg.getType());
        out.writeIntLE(msg.getCode());
        out.writeLongLE(msg.getTime());
        out.writeIntLE(msg.getLength());
        out.writeBytes(msg.getDocument());
        return;
    }
}

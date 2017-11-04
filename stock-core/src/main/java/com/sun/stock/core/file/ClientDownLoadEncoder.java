package com.sun.stock.core.file;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by zhikunsun on 2017/11/4.
 */
public class ClientDownLoadEncoder extends MessageToByteEncoder<FileDO> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, FileDO msg, ByteBuf out) throws Exception {
        out.writeByte(msg.getType());
        out.writeIntLE(msg.getCode());
        out.writeLongLE(msg.getTime());
        out.writeIntLE(msg.getLength());
        return;
    }
}

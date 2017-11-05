package com.sun.stock.core.file;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.ReferenceCountUtil;

/**
 * Created by zksun on 2017/8/19.
 */
public class ClientUploadEncoder extends MessageToByteEncoder<FileDO> {

    @Override
    protected void encode(ChannelHandlerContext ctx, FileDO msg, ByteBuf out) throws Exception {
        out.writeByte(msg.getType());
        out.writeIntLE(msg.getCode());
        out.writeLongLE(msg.getTime());
        out.writeIntLE(msg.getLength());
        if (null != msg.getDocument())
            out.writeBytes(msg.getDocument());
        return;
    }
}

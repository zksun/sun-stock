package com.sun.stock.core.file;

import com.sun.stock.core.common.logging.Logger;
import com.sun.stock.core.common.logging.LoggerFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by zhikunsun on 2017/11/4.
 */
public class FileDownloadHandler extends SimpleChannelInboundHandler<FileDO> {

    private final static Logger logger = LoggerFactory.getLogger(FileDownloadHandler.class.getName());

    private final String path;

    public FileDownloadHandler(String path) {
        this.path = path;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FileDO fileDO) throws Exception {

    }
}

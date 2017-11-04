package com.sun.stock.core.file;

import com.sun.stock.core.common.logging.Logger;
import com.sun.stock.core.common.logging.LoggerFactory;
import com.sun.stock.core.file.util.FileUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * Created by zhikunsun on 2017/11/4.
 */
public class ClientDownloadCallbackHandler extends SimpleChannelInboundHandler<FileDO> {

    private final static Logger logger = LoggerFactory.getLogger(ClientDownloadCallbackHandler.class.getName());

    private final String path;

    public ClientDownloadCallbackHandler(String path) {
        this.path = path;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FileDO msg) throws Exception {
        if (null == msg) {
            throw new RuntimeException("decode no fileDO");
        }

        String directoryName = FileUtils.getDirectoryName(msg.getType(), msg.getCode());
        String directory = FileUtils.createDirectory(path, directoryName);
        File file = FileUtils.filePath(directory, msg.getTime());

        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(0);
            randomAccessFile.write(msg.getDocument());
        } catch (Throwable throwable) {
            ctx.channel().writeAndFlush("failure");
            logger.error("store file failure with file:{}", throwable, file.getName());
        } finally {
            if (null != randomAccessFile) {
                randomAccessFile.close();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        logger.error("download file failure cause: " + cause);
        ctx.channel().close();
    }
}

package com.sun.stock.core.file;

import com.sun.stock.core.common.logging.Logger;
import com.sun.stock.core.common.logging.LoggerFactory;
import com.sun.stock.core.file.util.FileUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

import java.io.File;
import java.io.RandomAccessFile;


/**
 * Created by zksun on 2017/8/19.
 */
public class FileUploadHandler extends SimpleChannelInboundHandler<FileDO> {

    private final static Logger logger = LoggerFactory.getLogger(FileUploadHandler.class.getName());

    private final String path;

    public FileUploadHandler(String path) {
        this.path = path;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FileDO msg) throws Exception {
        if (null == msg) {
            ctx.channel().writeAndFlush("failure");
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
            ctx.channel().writeAndFlush("success");
        } catch (Throwable throwable) {
            ctx.channel().writeAndFlush("failure");
        } finally {
            if (null != randomAccessFile) {
                randomAccessFile.close();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        logger.error("upload file failure cause: " + cause);
        ctx.channel().close();
    }
}

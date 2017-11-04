package com.sun.stock.core.file;

import com.sun.stock.core.common.logging.Logger;
import com.sun.stock.core.common.logging.LoggerFactory;
import com.sun.stock.core.file.util.FileUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.*;

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
    protected void channelRead0(ChannelHandlerContext ctx, FileDO msg) throws Exception {
        if (null == msg) {
            ctx.channel().writeAndFlush("failure");
            throw new RuntimeException("decode no fileDO");
        }

        String directoryName = FileUtils.getDirectoryName(msg.getType(), msg.getCode());
        String directory = FileUtils.createDirectory(path, directoryName);
        File file = FileUtils.filePath(directory, msg.getTime());
        if (file.exists() && file.isFile()) {
            BufferedInputStream bufferedInputStream = null;
            try {
                bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
                int available = bufferedInputStream.available();
                byte[] fileBytes;
                bufferedInputStream.read(fileBytes = new byte[available]);
                if (null != fileBytes || fileBytes.length > 0) {
                    FileDO fileDO = new FileDO();
                    fileDO.setType(msg.getType());
                    fileDO.setCode(msg.getCode());
                    fileDO.setTime(msg.getTime());
                    fileDO.setLength(fileBytes.length);
                    fileDO.setDocument(fileBytes);
                    ctx.writeAndFlush(fileDO);
                }
            } catch (Exception e) {
                logger.error("download file error: ", e);
            } finally {
                if (null != bufferedInputStream) {
                    bufferedInputStream.close();
                }
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

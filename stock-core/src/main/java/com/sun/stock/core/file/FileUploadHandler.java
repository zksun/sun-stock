package com.sun.stock.core.file;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.File;
import java.io.RandomAccessFile;

import static com.sun.stock.core.test.file.FileUtils.*;

/**
 * Created by zksun on 2017/8/19.
 */
public class FileUploadHandler extends SimpleChannelInboundHandler<FileDO> {

    private final String path;

    public FileUploadHandler(String path) {
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
        } finally {
            if (null != randomAccessFile) {
                randomAccessFile.close();
            }
        }

    }
}

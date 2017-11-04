package com.sun.stock.core.file;

import com.sun.stock.core.common.logging.Logger;
import com.sun.stock.core.common.logging.LoggerFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * Created by zhikunsun on 2017/11/4.
 */
public class FileDownloadClient {

    private final int MAX_FRAME_LENGTH = 1024 * 1024;
    private final int LENGTH_FILE_LENGTH = 4;
    private final int LENGTH_FILE_OFFSET = 13;
    private final int LENGTH_ADJUSTMENT = 0;
    private final int INITIAL_BYTES_TO_STRIP = 0;


    private final static Logger logger = LoggerFactory.getLogger(FileUploadClient.class);

    private final static EventLoopGroup group = new NioEventLoopGroup();

    public Channel connect(SocketAddress address) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast(new ClientDownLoadEncoder());
                        ch.pipeline().addLast(new FileDecoder(MAX_FRAME_LENGTH,
                                LENGTH_FILE_OFFSET,
                                LENGTH_FILE_LENGTH,
                                LENGTH_ADJUSTMENT,
                                INITIAL_BYTES_TO_STRIP,
                                false));
                        ch.pipeline().addLast(new ClientCallbackHandler());
                    }
                });
        try {
            ChannelFuture sync = bootstrap.connect(address).sync();
            return sync.channel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        final Channel channel;
        FileDownloadClient fileDownloadClient = new FileDownloadClient();
        FileUploadClient client = new FileUploadClient();
        SocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 3128);
        channel = client.connect(socketAddress);
        FileDO fileDO = new FileDO();
        fileDO.setTime(20170821L);
        fileDO.setCode(600352);
        fileDO.setType((byte) 1);
        fileDO.setLength(0);

        ChannelFuture channelFuture = channel.writeAndFlush(fileDO);
        try {
            channelFuture.sync();
        } catch (InterruptedException e) {
            logger.error("", e);
        }
    }

}
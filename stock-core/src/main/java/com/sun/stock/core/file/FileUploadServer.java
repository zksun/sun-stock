package com.sun.stock.core.file;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by zksun on 2017/8/18.
 */
public class FileUploadServer {

    private final int MAX_FRAME_LENGTH = 1024 * 1024;
    private final int LENGTH_FILE_LENGTH = 4;
    private final int LENGTH_FILE_OFFSET = 13;
    private final int LENGTH_ADJUSTMENT = 0;
    private final int INITIAL_BYTES_TO_STRIP = 0;

    public void bind(int port, String path) {
        final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128).childHandler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline().addLast(new ServerDecoder(MAX_FRAME_LENGTH, LENGTH_FILE_OFFSET, LENGTH_FILE_LENGTH, LENGTH_ADJUSTMENT, INITIAL_BYTES_TO_STRIP, false))
                        .addLast(new FileUploadHandler(path));
            }
        });
        try {
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new FileUploadServer().bind(3128,"/Users/hanshou/Documents/stocks");
    }
}

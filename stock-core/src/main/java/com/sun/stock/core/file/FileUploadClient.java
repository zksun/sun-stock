package com.sun.stock.core.file;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * Created by zksun on 2017/8/19.
 */
public class FileUploadClient {

    private final static EventLoopGroup group = new NioEventLoopGroup();

    public Channel connect(SocketAddress address) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast(new ClientEncoder());
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
        FileUploadClient client = new FileUploadClient();
        SocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 3128);
        channel = client.connect(socketAddress);
        FileDO fileDO = new FileDO();
        fileDO.setTime(20170820L);
        fileDO.setCode(600352);
        fileDO.setType((byte) 0);
        try {
            RandomAccessFile r = new RandomAccessFile("/Users/hanshou/Documents/20150427.txt", "r");
            r.seek(0);
            fileDO.setLength((int) r.length());
            byte[] bytes = new byte[(int) r.length()];
            r.read(bytes);
            fileDO.setDocument(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ChannelFuture channelFuture = channel.writeAndFlush(fileDO);
        try {
            channelFuture.sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

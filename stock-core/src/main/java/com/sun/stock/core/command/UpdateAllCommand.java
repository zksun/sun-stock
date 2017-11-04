package com.sun.stock.core.command;

import com.sun.stock.core.common.logging.Logger;
import com.sun.stock.core.common.logging.LoggerFactory;
import com.sun.stock.core.common.util.DateUtils;
import com.sun.stock.core.file.FileDownloadClient;
import io.netty.channel.Channel;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.time.LocalDate;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by zhikunsun on 2017/11/5.
 */
public class UpdateAllCommand {
    private final static Logger logger = LoggerFactory.getLogger(UpdateAllCommand.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ChannelCreator channelCreator = new ChannelCreator();
        Executors.newSingleThreadExecutor().submit(channelCreator);

        while (channelCreator.channel == null) {
            synchronized (channelCreator) {
                channelCreator.wait(1000l);
            }
        }

        LocalDate start = DateUtils.getLocalDateYYYYMMDD(20170922L);
        Future<Boolean> submit = Executors.newSingleThreadExecutor().submit(new UpdateStockCommand(start,
                600352L, (byte) 0, channelCreator.channel, "/Users/zhikunsun/Documents/new_stock_data"));
        System.out.println(submit.get());
        channelCreator.channel.close();

    }

    private static class ChannelCreator implements Runnable {
        private Channel channel;

        @Override
        public void run() {
            FileDownloadClient fileDownloadClient = new FileDownloadClient();
            SocketAddress socketAddress = new InetSocketAddress("218.244.139.178", 65535);
            //SocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 3128);
            channel = fileDownloadClient.connect(socketAddress, "/Users/zhikunsun/Documents/new_stock_data");
            try {
                channel.closeFuture().sync();
                System.out.println("channel closed");
            } catch (InterruptedException e) {
                //ignore
            } finally {
                FileDownloadClient.group.shutdownGracefully();
            }
        }


    }

}

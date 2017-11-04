package com.sun.stock.core.command;

import com.sun.stock.core.common.logging.Logger;
import com.sun.stock.core.common.logging.LoggerFactory;
import com.sun.stock.core.common.util.DateUtils;
import com.sun.stock.core.file.FileDownloadClient;
import com.sun.stock.core.util.StockUtil;
import io.netty.channel.Channel;
import io.netty.channel.socket.InternetProtocolFamily;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

        LocalDate start = DateUtils.getLocalDateYYYYMMDD(20170923L);

        List<String> allStockDirectoryNames = StockUtil.getAllStockDirectoryNames("/Users/zhikunsun/Documents/stock_data");
        List<UpdateStockCommand> commands = null;
        if (null != allStockDirectoryNames && allStockDirectoryNames.size() > 0) {
            commands = new ArrayList<>();
            for (String stock : allStockDirectoryNames) {
                String[] stockCode = StockUtil.getStockCode(stock);
                byte type = StockUtil.getType(stockCode[0]);
                Integer code = Integer.valueOf(stockCode[1]);
                commands.add(new UpdateStockCommand(start,
                        code, type, channelCreator.channel));
            }
        }

        if (null != commands) {
            List<Future<Boolean>> futures = Executors.newSingleThreadExecutor().invokeAll(commands);
            System.out.println(futures);
        }

    }

    private static class ChannelCreator implements Runnable {
        private Channel channel;

        @Override
        public void run() {
            FileDownloadClient fileDownloadClient = new FileDownloadClient();
            SocketAddress socketAddress = new InetSocketAddress("218.244.139.178", 65535);
            //SocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 3128);
            channel = fileDownloadClient.connect(socketAddress, "/Users/zhikunsun/Documents/stock_data");
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

package com.sun.stock.core.file;

import com.sun.stock.core.command.UpdateStockCommand;
import com.sun.stock.core.common.logging.Logger;
import com.sun.stock.core.common.logging.LoggerFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by zksun on 2017/8/21.
 */
public class ClientCallbackHandler extends SimpleChannelInboundHandler<String> {
    private final static Logger logger = LoggerFactory.getLogger(ClientCallbackHandler.class.getName());

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        if (msg.equals("success")) {
            logger.info("send stock txt file success");
            UpdateStockCommand.okQueue.add(msg);
        } else if (msg.equals("failure")) {
            logger.info("send stock txt file failure");
        }
    }
}

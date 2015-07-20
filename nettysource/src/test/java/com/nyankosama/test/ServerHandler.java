package com.nyankosama.test;

import org.jboss.netty.channel.core.ChannelHandlerContext;
import org.jboss.netty.channel.core.impl.SimpleChannelHandler;
import org.jboss.netty.channel.event.ChannelStateEvent;
import org.jboss.netty.channel.event.MessageEvent;

/**
 * Created by Lichao.W At 2015/7/6 13:20
 * wanglichao@163.com
 */
public class ServerHandler extends SimpleChannelHandler {

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
//        e.getChannel().write(e.getMessage());
        byte[] bytes = (byte[]) e.getMessage();
        String msg = new String(bytes, "GBK");
        System.out.println("get message :" + msg);
    }

    @Override
    public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        super.channelOpen(ctx, e);
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        super.channelConnected(ctx, e);
    }
}

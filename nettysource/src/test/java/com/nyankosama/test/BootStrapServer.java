package com.nyankosama.test;


import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.core.ChannelPipeline;
import org.jboss.netty.channel.core.ChannelPipelineFactory;
import org.jboss.netty.channel.core.Channels;
import org.jboss.netty.channel.socket.nio.server.NioServerSocketChannelFactory;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Created by Lichao.W At 2015/7/6 13:20
 * wanglichao@163.com
 */
public class BootStrapServer {

    public void run() throws Exception{
        // Configure the server.
        ServerBootstrap bootstrap = new ServerBootstrap(
                new NioServerSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()));

        // Set up the pipeline factory.
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(new ServerHandler());
            }
        });

        // Bind and start to accept incoming connections.
        bootstrap.bind(new InetSocketAddress(9123));
        System.out.println("------ server started ------");
    }

    public static void main(String args[]) throws Exception {
        new BootStrapServer().run();
    }
}

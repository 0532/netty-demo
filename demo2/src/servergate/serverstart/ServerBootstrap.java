package servergate.serverstart;

import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servergate.codec.LFixedLengthMsgDecoder;
import servergate.codec.LFixedLengthMsgEncoder;
import utils.PropertyManager;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Created by Lichao.W At 2015/7/14 9:44
 * wanglichao@163.com
 */
public class ServerBootstrap {
    private static Logger logger = LoggerFactory.getLogger(ServerBootstrap.class);
    private static String SERVER_IP = PropertyManager.getProperty("dep.localhost.ip");
    private int port;

    public ServerBootstrap(int port) {
        this.port = port;
    }
    public void start() {
        ChannelFactory factory = new NioServerSocketChannelFactory(
                Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
        org.jboss.netty.bootstrap.ServerBootstrap bootstrap = new org.jboss.netty.bootstrap.ServerBootstrap(factory);
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() {
                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("decoder", new LFixedLengthMsgDecoder());
                pipeline.addLast("encoder", new LFixedLengthMsgEncoder());
                pipeline.addLast("serverChannelHandler", new ServerChannelHandler());
                return pipeline;
            }
        });
        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.keepAlive", true);
        bootstrap.bind(new InetSocketAddress(port));
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}

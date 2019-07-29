package lean.netty.discard.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import javax.net.ssl.SSLException;

/**
 * Created by 浩 on 2017/2/20.
 */
public class DiscardClient {

    static final boolean SSL = false;
    static final String HOST = "127.0.0.1";
    static final int PORT = 9999;
    static final int SIZE = 256;

    public static void main(String[] args) throws SSLException {

        final SslContext sslContext;
        if (SSL) {
            sslContext = SslContext.newClientContext(InsecureTrustManagerFactory.INSTANCE);
        } else {
            sslContext = null;
        }
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    if (sslContext != null) {
                        pipeline.addLast(sslContext.newHandler(ch.alloc(), HOST, PORT));
                    }
                    pipeline.addLast(new DiscardClientHandler());
                }
            });
            ChannelFuture future = bootstrap.connect(HOST, PORT);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            group.shutdownGracefully();
        }
    }
}

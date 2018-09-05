package lean.jdk8.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * Created by 浩 on 2016/9/29.
 */
public class SocketClient {

    public static void main(String[] args) throws IOException {

        String hostname = "192.168.0.116";
        int port = 54321;
        InetAddress address = InetAddress.getByName(hostname);

        final InetSocketAddress addr = new InetSocketAddress(address, port);
        final SocketChannel socketChannel = SocketChannel.open();

        socketChannel.configureBlocking(false);
        socketChannel.connect(addr);

    }
}

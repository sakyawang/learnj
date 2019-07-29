package lean.netty.udp.client;

import io.netty.util.CharsetUtil;
import lean.netty.udp.utils.UDPUtils;

import java.net.DatagramSocket;

import static lean.netty.udp.client.LocalUDPDataReceiver.*;

/**
 * @Description TODO
 * @Author wanghao@cncloudsec.com
 * @Date 19-3-25 上午11:39
 */
public class UDPClient {

    public static void main(String[] args) throws InterruptedException {
        LocalUDPSocketProvider localUDPSocketProvider = LocalUDPSocketProvider.getInstance();
        localUDPSocketProvider.initSocket(0, "127.0.0.1", 5140);
        DatagramSocket localUDPSocket = localUDPSocketProvider.getLocalUDPSocket();
        getInstance().startup();
        while (true) {
            String message = "to server";
            byte[] messageBytes = message.getBytes(CharsetUtil.UTF_8);
            UDPUtils.send(localUDPSocket, messageBytes, messageBytes.length);
            Thread.sleep(3000);
        }
    }
}

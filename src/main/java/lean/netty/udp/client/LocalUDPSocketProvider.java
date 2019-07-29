package lean.netty.udp.client;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * @Description TODO
 * @Author wanghao@cncloudsec.com
 * @Date 19-3-25 上午11:40
 */
public class LocalUDPSocketProvider {

    private static volatile LocalUDPSocketProvider instance = null;

    private DatagramSocket localUDPSocket = null;

    private LocalUDPSocketProvider(){}

    public static LocalUDPSocketProvider getInstance() {
        if (instance == null) {
            synchronized (LocalUDPSocketProvider.class) {
                if (instance == null) {
                    instance = new LocalUDPSocketProvider();
                }
            }
        }
        return instance;
    }

    public void initSocket(int localPort, String serverIp, int servePort) {
        try {
            // UDP本地监听端口（如果为0将表示由系统分配，否则使用指定端口）
            this.localUDPSocket = new DatagramSocket(localPort);
            // 调用connect之后，每次send时DatagramPacket就不需要设计目标主机的ip和port了
            // * 注意：connect方法一定要在DatagramSocket.receive()方法之前调用，
            // * 不然整send数据将会被错误地阻塞。这或许是官方API的bug，也或许是调
            // * 用规范就应该这样，但没有找到官方明确的说明
            this.localUDPSocket.connect(InetAddress.getByName(serverIp), servePort);
            this.localUDPSocket.setReuseAddress(true);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public DatagramSocket getLocalUDPSocket() {
        return this.localUDPSocket;
    }

}

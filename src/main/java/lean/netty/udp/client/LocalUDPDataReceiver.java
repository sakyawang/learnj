package lean.netty.udp.client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @Description TODO
 * @Author wanghao@cncloudsec.com
 * @Date 19-3-25 下午2:20
 */
public class LocalUDPDataReceiver {

    private static volatile LocalUDPDataReceiver instance = null;
    private Thread thread = null;

    public static LocalUDPDataReceiver getInstance() {
        if (instance == null) {
            synchronized (LocalUDPDataReceiver.class) {
                if (instance == null) {
                    instance = new LocalUDPDataReceiver();
                }
            }
        }
        return instance;
    }

    public void startup() {
        this.thread = new Thread(()->{
            try {
                //开始侦听
                LocalUDPDataReceiver.this.udpListeningImpl();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        this.thread.start();
    }

    private void udpListeningImpl() throws Exception {
        while (true) {
            byte[] data = new byte[1024];
            // 接收数据报的包
            DatagramPacket packet = new DatagramPacket(data, data.length);
            DatagramSocket localUDPSocket = LocalUDPSocketProvider.getInstance().getLocalUDPSocket();
            if ((localUDPSocket == null) || (localUDPSocket.isClosed()))
                continue;
            // 阻塞直到收到数据
            localUDPSocket.receive(packet);
            // 解析服务端发过来的数据
            String pFromServer = new String(packet.getData(), 0, packet.getLength(), "UTF-8");
            System.out.println(pFromServer);
        }
    }
}
package lean.netty.udp.utils;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @Description TODO
 * @Author wanghao@cncloudsec.com
 * @Date 19-3-25 下午2:30
 */
public class UDPUtils {

    public static boolean send(DatagramSocket skt, byte[] d, int dataLen) {
        if (skt != null && d != null) {
            try {
                return send(skt, new DatagramPacket(d, dataLen));
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public synchronized static boolean send(DatagramSocket skt, DatagramPacket p) {
        boolean sendSucess = true;
        if (skt != null && p != null) {
            if (skt.isConnected()) {
                try {
                    skt.send(p);
                } catch (Exception e) {
                    sendSucess = false;
                }
            }
        } else {
        }

        return sendSucess;
    }
}

package lean.socket.server;

import lean.socket.client.ThreadSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by wanghao@weipass.cn on 2015/10/16.
 */
public class MutiSocketServer {

    public static void main(String[] args) {

        // TODO Auto-generated method stub
        ServerSocket server = null;
        try {
            server = new ServerSocket(8888);
            System.out.println("send服务器start！");
            Socket sock = null;
            while (true) {
                sock = server.accept();
                sock.setSoTimeout(12000);//设置读取连接超时时间
                ThreadSocket tsock = new ThreadSocket(sock);
                new Thread(tsock).start();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }/*finally{  //这里还是不要finally关闭ServerSocket为好，防止某个socket连接超时导致整个ServerSocket都关闭了。
            try {
                server.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }*/

    }

}

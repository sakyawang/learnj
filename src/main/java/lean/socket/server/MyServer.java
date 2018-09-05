package lean.socket.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by wanghao@weipass.cn on 2015/10/16.
 */
public class MyServer {

    public static void main(String[] args) {

        try {
            ServerSocket server = new ServerSocket(888);

            Socket client = server.accept();

            InputStream in = client.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            System.out.println(reader.readLine());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

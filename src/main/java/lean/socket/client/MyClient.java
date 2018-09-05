package lean.socket.client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by wanghao@weipass.cn on 2015/10/16.
 */
public class MyClient {

    public static void main(String[] args) {

        try {
            Socket socket = new Socket("127.0.0.1",888);
            OutputStream out = socket.getOutputStream();
            PrintWriter print = new PrintWriter(out);
            print.write("sadd");
            print.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

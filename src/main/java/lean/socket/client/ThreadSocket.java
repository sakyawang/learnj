package lean.socket.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Created by wanghao@weipass.cn on 2015/10/16.
 */
public class ThreadSocket implements Runnable {

    private Socket sock;
    public ThreadSocket(Socket sock){
        this.sock=sock;
    }

    /*
     * <p>Title: run</p>
     * <p>Description: </p>
     * @see java.lang.Runnable#run()
     */
    public void run() {
        // TODO Auto-generated method stub
        InputStream input = null;
        try {
            input = sock.getInputStream();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        /*  //字符流的形式读取(遇到换行符或者回车符就终止，还是谨慎使用)
           BufferedReader read=new BufferedReader(new InputStreamReader(input));
           String readStr=null;
           try {
            if((readStr=read.readLine())!=null){
                   System.out.println("服务器端收到的报文：\n"+readStr);
               }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

           if(read!=null) {
               try {
                read.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
           }*/

        //字节流
        byte[] buf = new byte[1024];
        if (input != null) {
            int len = 0;
            try {
                len = input.read(buf);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("服务器端收到的报文：\n" + new String(buf, 0, len));
        }
    }
}

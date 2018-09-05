package lean.socket.server;

import lean.serialize.Address;
import lean.serialize.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by wanghao@weipass.cn on 2015/10/16.
 */
public class SocketServer {

    public static void main(String args[]) {

        try{

            ServerSocket server = null;

            try{

                server = new ServerSocket(4700);

                //创建一个ServerSocket在端口4700监听客户请求

            }catch(Exception e) {

                System.out.println("can not listen to:"+e);

                //出错，打印出错信息
            }

            Socket socket = null;

            try{

                socket = server.accept();

                //使用accept()阻塞等待客户请求，有客户

                //请求到来则产生一个Socket对象，并继续执行
                InetAddress inetAddress = socket.getInetAddress();
            }catch(Exception e) {
                System.out.println("Error."+e);
                //出错，打印出错信息
            }

            String line;

            BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //由Socket对象得到输入流，并构造相应的BufferedReader对象

//            PrintWriter os = new PrintWriter(socket.getOutputStream());

            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            //由Socket对象得到输出流，并构造PrintWriter对象

            BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));

            //由系统标准输入设备构造BufferedReader对象

//            System.out.println("Client:"+is.readLine());

            //在标准输出上打印从客户端读入的字符串
            String readLine = is.readLine();
            //从标准输入读入一字符串
            User user = new User();
            Address address = new Address();
            address.setCountry("中国");
            address.setProvince("河南省");
            address.setCity("新乡市");
            address.setStreet("榆林乡万泉庄");
            user.setName("王浩");
            user.setAge(29);
            user.setAddress(address);
            while(!readLine.equals("bye")){

                //如果该字符串为 "bye"，则停止循环

                //刷新输出流，使Client马上收到该字符串

                //在系统标准输出上打印读入的字符串

                System.out.println("Client:"+readLine);

                os.writeObject(user);
                //向客户端输出该字符串
                os.flush();

                //从Client读入一字符串，并打印到标准输出上

//                line = sin.readLine();

                //从系统标准输入读入一字符串

            } //继续循环

            os.close(); //关闭Socket输出流

            is.close(); //关闭Socket输入流

            socket.close(); //关闭Socket

            server.close(); //关闭ServerSocket

        }catch(Exception e){
            System.out.println("Error:"+e);
            //出错，打印出错信息
        }
    }
}

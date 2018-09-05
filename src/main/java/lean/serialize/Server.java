package lean.serialize;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by 浩 on 2017/4/21.
 */
public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(8888));
        Socket socket = serverSocket.accept();
        InputStream inputStream = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = reader.readLine();
        OutputStream outputStream = socket.getOutputStream();
        User user = new User();
        Address address = new Address();
        address.setCountry("中国");
        address.setProvince("河南省");
        address.setCity("新乡市");
        address.setStreet("榆林乡万泉庄");
        user.setName("王浩");
        user.setAge(29);
        user.setAddress(address);
        System.out.println(line);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(user);
        objectOutputStream.flush();
    }
}

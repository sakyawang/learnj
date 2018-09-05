package lean.serialize;

import java.io.*;
import java.net.Socket;

/**
 * Created by 浩 on 2017/4/21.
 */
public class Client {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Socket socket = new Socket("127.0.0.1", 8888);
        OutputStream outputStream = socket.getOutputStream();
        PrintWriter printWriter = new PrintWriter(outputStream);
        printWriter.write("get user");
        printWriter.flush();
        InputStream inputStream = socket.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        User user = (User) objectInputStream.readObject();
        System.out.println(user.getName());
    }
}

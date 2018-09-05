package lean.rpc;

import lean.rpc.base.RpcRequest;
import lean.rpc.base.RpcResponse;
import lean.rpc.user.po.User;
import lean.rpc.user.po.UserParam;
import lean.rpc.user.service.UserService;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by 浩 on 2017/5/11.
 */
public class RpcServer {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(8888));
        Socket socket = serverSocket.accept();
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        ObjectInputStream stream = new ObjectInputStream(inputStream);
        RpcRequest<UserParam> request = (RpcRequest<UserParam>) stream.readObject();
        UserService userService = new UserService();
        RpcResponse<User> response = userService.execute(request);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(response);
        objectOutputStream.flush();
    }
}

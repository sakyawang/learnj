package lean.rpc;

import lean.rpc.base.RpcRequest;
import lean.rpc.base.RpcResponse;
import lean.rpc.user.po.User;
import lean.rpc.user.po.UserParam;

import java.io.*;
import java.net.Socket;

/**
 * Created by 浩 on 2017/5/11.
 */
public class RpcClient {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket = new Socket("10.2.6.20", 8888);
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream stream = new ObjectOutputStream(outputStream);
        RpcRequest<UserParam> request = new RpcRequest<>();
        UserParam param = new UserParam();
        param.setName("wanghao");
        request.setParams(param);
        stream.writeObject(request);
        stream.flush();
        InputStream inputStream = socket.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        RpcResponse<User> response = (RpcResponse<User>) objectInputStream.readObject();
        System.out.println(response.getMessage());
        System.out.println(response.getResult().getAge());
    }
}

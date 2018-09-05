package lean.socket.client;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by 浩 on 2017/3/9.
 */
public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        String ip = "10.2.7.4";
        int port = 8001;

        Executor pool = Executors.newFixedThreadPool(50);
        CountDownLatch latch = new CountDownLatch(50);
        for(int i = 0;i < 50;i++){
            int type = new Random().nextInt(3) + 3;
            System.out.println(type);
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    byte[] jsonByte = new byte[0];
                    byte[] lenByte = ByteUtil.cInt2byteArray(8);
                    byte[] typeByte = ByteUtil.cInt2byteArray(type);
                    byte[] message = ByteUtil.arraycopy(lenByte, typeByte, jsonByte);
//                    SocketClientFactory.FlqSocketClient client1 = SocketClientFactory.getClient(ip, port);
                    System.out.println(type);
                    MsgStruct msgStruct = new MsgStruct();
                    msgStruct.setData(message);
                    FlqClient flqClient = new FlqClient(ip, port);
                    byte[] result = flqClient.request(msgStruct);
                    System.out.println(ByteUtil.bytetoString(result));
                    latch.countDown();
                }
            });
        }
        latch.await();
        System.exit(0);
    }
}

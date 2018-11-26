package lean.thread;

import java.net.Socket;
import java.util.concurrent.CountDownLatch;

/**
 * Created by 浩 on 2017/3/9.
 */
public class ReadThread implements Runnable{

    private Socket socket;

    private CountDownLatch countDownLatch;

    public ReadThread(){}

    public ReadThread(Socket socket, CountDownLatch countDownLatch){
        this.socket = socket;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        System.out.println("read");
        throw new RuntimeException("error");
    }

}

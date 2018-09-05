package lean.thread;

import java.io.BufferedOutputStream;
import java.util.concurrent.CountDownLatch;

/**
 * Created by 浩 on 2017/3/9.
 */
public class WriteThread implements Runnable {

    private CountDownLatch countDownLatch;

    private BufferedOutputStream writer;

    public WriteThread(CountDownLatch countDownLatch, BufferedOutputStream writer){
        this.countDownLatch = countDownLatch;
        this.writer = writer;
    }

    @Override
    public void run() {

    }

}

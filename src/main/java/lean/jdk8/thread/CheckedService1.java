package lean.jdk8.thread;

import java.util.concurrent.CountDownLatch;

/**
 * Created by 浩 on 2016/9/29.
 */
public class CheckedService1 extends AbstractCheckedService {

    public CheckedService1(String serviceName) {
        super(serviceName);
    }

    public CheckedService1(String serviceName, CountDownLatch latch) {
        super(serviceName, latch);
    }

    @Override
    public boolean verify() {
        System.out.println("verify " + this.getServiceName());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.getServiceName() + "is UP");
        return true;
    }
}

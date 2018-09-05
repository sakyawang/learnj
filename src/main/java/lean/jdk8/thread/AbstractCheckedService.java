package lean.jdk8.thread;

import java.util.concurrent.CountDownLatch;

/**
 * Created by 浩 on 2016/9/29.
 */
public abstract class AbstractCheckedService implements Runnable {

    private CountDownLatch latch;

    private String serviceName;

    private boolean serviceUp;

    public AbstractCheckedService(String serviceName){
        this.serviceName = serviceName;
    }

    public AbstractCheckedService(String serviceName, CountDownLatch latch){
        super();
        this.latch = latch;
        this.serviceName = serviceName;
        this.serviceUp = false;
    }

    @Override
    public void run() {

        try {
            serviceUp = verify();
        } catch (Exception e) {
            e.printStackTrace();
            serviceUp = false;
        } finally {
            if (latch != null){
                latch.countDown();
            }
        }
    }

    public abstract boolean verify();

    public CountDownLatch getLatch() {
        return latch;
    }

    public AbstractCheckedService setLatch(CountDownLatch latch) {
        this.latch = latch;
        return this;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public boolean isServiceUp() {
        return serviceUp;
    }

    public void setServiceUp(boolean serviceUp) {
        this.serviceUp = serviceUp;
    }
}

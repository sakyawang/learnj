package lean.jdk8.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by 浩 on 2016/10/9.
 */
public class FutureTest {

    public Future request(){

        final String name = "wanghao";
        final Future future = new Future() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public Object get() throws InterruptedException, ExecutionException {
                return name;
            }

            @Override
            public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return null;
            }
        };
        new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("----donging business---------");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.run();
        return future;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        FutureTest futureTest = new FutureTest();
        Future future = futureTest.request();
        System.out.println(future.get());
    }
}

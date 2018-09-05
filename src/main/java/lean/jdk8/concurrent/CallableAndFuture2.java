package lean.jdk8.concurrent;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by 浩 on 2016/9/29.
 */
public class CallableAndFuture2 {

    public static void main(String[] args) {

        ExecutorService threadExecutor = Executors.newSingleThreadExecutor();
        Future<Integer> future = threadExecutor.submit(new Callable<Integer>() {

            @Override
            public Integer call() throws Exception {
                return new Random().nextInt(100);
            }
        });
        try {
            Thread.sleep(5000);
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

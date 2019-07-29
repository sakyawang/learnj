package lean.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description TODO
 * @Author wanghao@cncloudsec.com
 * @Date 18-12-20 上午11:27
 */
public class ThreadLocalTest {

    private static final ThreadLocal<String> local = new ThreadLocal<>();

    public static void setName(String name) {
        local.set(name);
    }

    public static String getName() {
        return local.get();
    }

    public static int age = 0;

    public static void main(String[] args) {
        /*ExecutorService threadPool = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 5; i++) {
            String name = "name" + i;
            threadPool.submit(() -> {
                String threadName = Thread.currentThread().getName();
                ThreadLocalTest.setName(threadName + "-" + name);
                System.out.println(threadName + ":" + ThreadLocalTest.getName());
            });
        }
        threadPool.shutdown();*/
        /*System.out.println(ThreadLocalTest.age);
        ThreadLocalTest.age = 20;
        System.out.println(ThreadLocalTest.age);*/
        ExecutorService threadPool = Executors.newFixedThreadPool(2);
        threadPool.submit(() -> {
            String threadName = Thread.currentThread().getName();
            ThreadLocalTest.setName(threadName);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(ThreadLocalTest.getName());
        });
        threadPool.submit(() -> {
            String threadName = Thread.currentThread().getName();
            ThreadLocalTest.setName(threadName);
            System.out.println(ThreadLocalTest.getName());
        });
        threadPool.submit(() -> {
            ThreadLocalTest.age = 20;
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(ThreadLocalTest.age);
        });
        threadPool.submit(() -> {
            ThreadLocalTest.age = 30;
            System.out.println(ThreadLocalTest.age);
        });
        threadPool.shutdown();
    }
}

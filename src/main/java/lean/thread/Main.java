package lean.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(1);

    public static void main(String[] args) {
        ReadThread thread = new ReadThread();
        Thread thread1 = new Thread(thread);
        thread1.setUncaughtExceptionHandler((Thread t, Throwable e) -> e.printStackTrace());
        thread1.start();
        try {
            executorService.submit(thread);
        } catch (Exception e) {
            e.printStackTrace();
        }
        WriteThread writeThread = new WriteThread();
        executorService.submit(writeThread);
        executorService.shutdown();
    }
}

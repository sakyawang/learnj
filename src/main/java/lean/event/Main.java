package lean.event;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        TaskService service = TaskService.getInstance();
        service.deal();
        ExecutorService threadPool = Executors.newFixedThreadPool(2);
        try {
            Thread.sleep(1000 * 60 * 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 100; i++) {
            Writer writer = new Writer("task" + i, service);
            threadPool.submit(writer);
        }
    }
}

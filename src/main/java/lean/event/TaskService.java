package lean.event;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

public class TaskService<T extends Task> {

    public static final BlockingQueue<Task> taskQueue = new LinkedBlockingDeque<>();

    public volatile int count = 0;

    private static class SingletonHolder {
        public static final TaskService INSTANCE = new TaskService();
    }

    public synchronized void count() {
        this.count ++;
        System.out.println(count);
    }
    public static TaskService getInstance() {
        return TaskService.SingletonHolder.INSTANCE;
    }

    public void add(T t) {
        taskQueue.add(t);
    }

    public void deal() {
        Worker worker = new Worker(taskQueue);
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.submit(worker);
        System.out.println("-------");
    }
}

package lean.event;

import java.util.concurrent.BlockingQueue;

public class Worker extends Thread {

    private BlockingQueue<Task> taskQueue;

    public Worker(BlockingQueue<Task> taskQueue) {
        this.taskQueue = taskQueue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Task task = taskQueue.take();
                System.out.println(task.getName());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

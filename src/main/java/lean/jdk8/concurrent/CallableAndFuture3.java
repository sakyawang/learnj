package lean.jdk8.concurrent;

import java.util.concurrent.*;

/**
 * Created by 浩 on 2016/9/29.
 */
public class CallableAndFuture3 {

    public static void main(String[] args) {

        ExecutorService threadPool = Executors.newCachedThreadPool();
        ExecutorCompletionService<Integer> completionService = new ExecutorCompletionService<>(threadPool);
        for (int i = 1;i < 5;i++){
            final int taskId = i;
            completionService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    return taskId;
                }
            });
        }
        for (int i = 1;i < 5;i++){
            try {
                System.out.println(completionService.take().get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}

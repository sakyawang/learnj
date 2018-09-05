package lean.jdk8.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 浩 on 2016/9/29.
 */
public class ApplicationStartupUtil {

    private static List<AbstractCheckedService> checkedService;

    private static CountDownLatch latch;

    private ApplicationStartupUtil() {
    }

    private static final ApplicationStartupUtil instance = new ApplicationStartupUtil();

    public static ApplicationStartupUtil getInstance() {
        return instance;
    }

    public static boolean verify() throws InterruptedException {

        checkedService = new ArrayList<AbstractCheckedService>() {
            {
                add(new CheckedService1("CheckedService1"));
                add(new CheckedService2("CheckedService2"));
                add(new CheckedService3("CheckedService3"));
            }
        };
        latch = new CountDownLatch(checkedService.size());
        final ExecutorService threadPool = Executors.newFixedThreadPool(checkedService.size());
        for (final AbstractCheckedService service : checkedService) {
            threadPool.execute(service.setLatch(latch));
        }
        latch.await();
        for (final AbstractCheckedService service : checkedService) {
            if (!service.isServiceUp()) {
                return false;
            }
        }
        return true;
    }

}

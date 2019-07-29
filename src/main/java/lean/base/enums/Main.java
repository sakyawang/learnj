package lean.base.enums;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description TODO
 * @Author wanghao@cncloudsec.com
 * @Date 19-5-7 下午4:45
 */
public class Main {

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 10; i++) {
            int num = i;
            service.submit(() ->  {
               /* try {
                    Thread.sleep((10 - num) * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                System.out.println(Thread.currentThread().getName() + ":" + DateRange.HOUR.startTime());
            });
        }
        service.shutdown();
    }
}

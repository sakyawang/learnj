package lean.file;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static lean.file.FileCopy.copy;
import static lean.file.FileCopy.writeFile;

/**
 * @Description TODO
 * @Author wanghao@cncloudsec.com
 * @Date 18-12-18 下午5:21
 */
public class Main {

    public static void main(String[] args) throws IOException {
        final String filePath = "/home/sakyawang/workspace/csaconfig.md";
        String copyFile = "/home/sakyawang/workspace/";
        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            String fileName = copyFile + i + ".md";
            long time = 1000 * (10 - i);
            int turn = i;
            service.submit(() -> {
                try {
//                    Thread.sleep(time);
                    System.out.println("thread:" + turn);
                    writeFile(fileName, copy(filePath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        service.shutdown();
    }

}

package lean.nio.channel.mutithread;

import com.google.common.base.Stopwatch;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang.StringUtils.leftPad;

/**
 * @Description TODO
 * @Author wanghao@cncloudsec.com
 * @Date 19-3-5 下午3:14
 */
public class FileUtil {

    public List<FileInfo> split(String originFile, String partDir, long splitSize) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        List<FileInfo> parts = new ArrayList<>();
        File file = new File(originFile);
        long length = file.length();
        int count = (int) Math.ceil(length / (double) splitSize);
        int countLen = (count + "").length();
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(count,
                count * 3, 1, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(count * 2));

        for (int i = 0; i < count; i++) {
            String partFileName = file.getName() + "."
                    + leftPad((i + 1) + "", countLen, '0') + ".part";
            FileInfo fileInfo = new FileInfo();
            fileInfo.setOriginFileName(originFile);
            fileInfo.setPartFileName(partDir + "/" + partFileName);
            fileInfo.setChunks(count);
            fileInfo.setChunkIndex(i);
            fileInfo.setSize(splitSize);
            long realSize = splitSize;
            if (i == (count - 1) && count * splitSize > length) {
                realSize = length - (count - 1) * splitSize;
            }
            fileInfo.setRealSize(realSize);
            threadPool.execute(new FileSplitThread(fileInfo));
            parts.add(fileInfo);
        }
        threadPool.shutdown();
        long nanos = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        System.out.println("split use time is : " + nanos + " ms");
        return parts;
    }

    public void merge(String dstFile, List<FileInfo> splitFiles) throws IOException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                splitFiles.size(), splitFiles.size() * 3, 1, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(splitFiles.size() * 2));
        long totalSize = splitFiles.stream().mapToLong(file -> file.getRealSize()).sum();
        RandomAccessFile mergeFile = new RandomAccessFile(dstFile, "rw");
        mergeFile.setLength(totalSize);
        mergeFile.close();
        for (int i = 0; i < splitFiles.size(); i++) {
            threadPool.execute(new FileMergeThread(splitFiles.get(i), dstFile));
        }
        threadPool.shutdown();
        long nanos = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        System.out.println("merge use time is : " + nanos + " ms");
    }

    public static void main(String[] args) throws IOException {
        String originFile = "/home/sakyawang/tmp/sc.tar.gz";
        String dstFile = "/home/sakyawang/tmp/sc/merge.tar.gz";
        String partDir = "/home/sakyawang/tmp/sc";
        long splitSize = 50 * 1024 * 1024;
        FileUtil fileUtil = new FileUtil();
        List<FileInfo> partFiles = fileUtil.split(originFile, partDir, splitSize);
        partFiles.forEach(file -> System.out.println(file.toString()));
        fileUtil.merge(dstFile, partFiles);
    }
}

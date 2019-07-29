package lean.nio.channel;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @Description TODO
 * @Author wanghao@cncloudsec.com
 * @Date 19-3-1 下午3:06
 */
public class FileUtil {

    public static void split(String originFile, String dstPath, int number) throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile(new File(originFile), "r");
        long length = accessFile.length();
        System.out.println("input file length is " + length);
        long maxSize = length / number;

        System.out.println("maxsize is " + maxSize);
        for (int i = 0; i < number; i++) {
            long begin = i * maxSize;
            long count = maxSize;
            if(i == (number -1) && maxSize * number < length) {
                count = length - (number-1) * maxSize;
            }
            writeSplitFile(accessFile, dstPath, begin, count, i);
        }
    }

    private static void writeSplitFile(RandomAccessFile in, String dstPath, long begin, long count, int i) throws IOException {
        FileChannel inChannel = in.getChannel();
        RandomAccessFile out = new RandomAccessFile(new File(dstPath + "." + i), "rw");
        FileChannel outChannel = out.getChannel();
        long length = inChannel.transferTo(begin, count, outChannel);
        System.out.println("write length " + length);
        outChannel.close();
        out.close();
    }


    public static void merge(String dstFile, String originFilePrefix, int number) throws IOException {
        RandomAccessFile out = new RandomAccessFile(new File(dstFile), "rw");
        FileChannel outChannel = out.getChannel();
        for (int i = 0; i < number; i++) {
            RandomAccessFile in = new RandomAccessFile(originFilePrefix + "." + i, "r");
            FileChannel inChannel = in.getChannel();
            outChannel.transferFrom(inChannel, outChannel.position(), inChannel.size());
            outChannel.position(outChannel.size());
        }
    }

    public static void disorderMerge(RandomAccessFile out, String splitFile, long position) throws IOException {
        RandomAccessFile in = new RandomAccessFile(splitFile, "r");
        FileChannel outChannel = out.getChannel();
        FileChannel inChannel = in.getChannel();
        outChannel.transferFrom(inChannel, position, inChannel.size());
        inChannel.close();
        in.close();
    }

    public static void main(String[] args) throws IOException {
        /*String originFile = "/home/sakyawang/tmp/dev.md";
        String dstPath = "/home/sakyawang/tmp/test/dev.tmp";
        String dstFile = "/home/sakyawang/tmp/test/dev.md";*/
        String originFile = "/home/sakyawang/tmp/tx.mp4";
        String dstPath = "/home/sakyawang/tmp/tx/tx.tmp";
        String dstFile = "/home/sakyawang/tmp/tx/tx.mp4";
        int splitNumber = 5;
//        FileUtil.split(originFile, dstPath, splitNumber);
//        FileUtil.merge(dstFile, dstPath, splitNumber);
        RandomAccessFile out = new RandomAccessFile(dstFile, "rw");
        out.setLength(new File(originFile).length());
        FileUtil.disorderMerge(out, dstPath+".3", 3 * 4357174);
        FileUtil.disorderMerge(out, dstPath+".2", 2 * 4357174);
        FileUtil.disorderMerge(out, dstPath+".4", 4 * 4357174);
        FileUtil.disorderMerge(out, dstPath+".1", 1 * 4357174);
        FileUtil.disorderMerge(out, dstPath+".0", 0);
        out.close();
    }
}

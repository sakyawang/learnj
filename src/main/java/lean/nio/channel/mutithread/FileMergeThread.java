package lean.nio.channel.mutithread;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @Description TODO
 * @Author wanghao@cncloudsec.com
 * @Date 19-3-5 下午3:15
 */
public class FileMergeThread implements Runnable {

    private FileInfo fileInfo;

    private String dstFile;

    public FileMergeThread(FileInfo fileInfo, String dstFile) {
        this.fileInfo = fileInfo;
        this.dstFile = dstFile;
    }

    @Override
    public void run() {

        try(RandomAccessFile in =  new RandomAccessFile(fileInfo.getPartFileName(), "r");
            RandomAccessFile out = new RandomAccessFile(dstFile, "rw");
            FileChannel inChannel = in.getChannel();
            FileChannel outChannel = out.getChannel()) {
            int chunkIndex = fileInfo.getChunkIndex();
            long size = fileInfo.getSize();
            outChannel.transferFrom(inChannel, chunkIndex * size, inChannel.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileInfo getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(FileInfo fileInfo) {
        this.fileInfo = fileInfo;
    }

    public String getDstFile() {
        return dstFile;
    }

    public void setDstFile(String dstFile) {
        this.dstFile = dstFile;
    }
}

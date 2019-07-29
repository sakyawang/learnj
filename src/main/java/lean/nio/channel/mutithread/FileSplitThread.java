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
public class FileSplitThread implements Runnable {

    private FileInfo fileInfo;

    public FileSplitThread(FileInfo fileInfo) {
        this.fileInfo = fileInfo;
    }

    @Override
    public void run() {
        try(RandomAccessFile in = new RandomAccessFile(fileInfo.getOriginFileName(), "r");
            RandomAccessFile out = new RandomAccessFile(fileInfo.getPartFileName(), "rw");
            FileChannel inChannel = in.getChannel();
            FileChannel outChannel = out.getChannel()) {
            long startPosition = fileInfo.getChunkIndex() * fileInfo.getSize();
            inChannel.transferTo(startPosition, fileInfo.getRealSize(), outChannel);
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
}

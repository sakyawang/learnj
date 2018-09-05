package lean.nio.channel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;

/**
 * Created by IntelliJ IDEA.
 * User: wanghao@weipass.cn
 * Date: 2016/5/17
 * Time: 18:01
 */
public class FileChannelTest {


    public static void read(String file) throws IOException {

        Charset charset = Charset.forName("utf-8");
        CharsetDecoder decoder = charset.newDecoder();
        RandomAccessFile aFile = new RandomAccessFile(file, "rw");
        FileChannel inChannel = aFile.getChannel();
        ByteBuffer buf = ByteBuffer.allocate(1024);
        CharBuffer charBuffer = CharBuffer.allocate(1024);
        int bytesRead = inChannel.read(buf);
        while (bytesRead != -1) {
            System.out.println("Read " + bytesRead);
            buf.flip();
            CoderResult decode = decoder.decode(buf, charBuffer, false);
            charBuffer.flip();
            System.out.println(charBuffer);
            charBuffer.clear();
            buf.clear();
            bytesRead = inChannel.read(buf);
        }
        inChannel.close();
        aFile.close();
    }

    private static void write(String file, String text) throws IOException {

        RandomAccessFile aFile = new RandomAccessFile(file, "rw");
        FileChannel inChannel = aFile.getChannel();
        ByteBuffer buf = ByteBuffer.allocate(100);
        buf.put(text.getBytes("utf-8"));
        buf.flip();
        while(buf.hasRemaining()){
            inChannel.write(buf);
        }
        inChannel.close();
        aFile.close();
    }

    public static void readLine(String file) throws FileNotFoundException {

        RandomAccessFile accessFile = new RandomAccessFile(file, "rw");
        FileChannel channel = accessFile.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        CharBuffer charBuffer = CharBuffer.allocate(1024);
    }

    public static void main(String[] args) throws IOException {

        String text = "测试中文写入";
        write("d:/lean.txt",text);
        read("d:/lean.txt");
        System.out.println("----------------");
        write("d:/lean.txt","测试英文");
        read("d:/lean.txt");
    }

}

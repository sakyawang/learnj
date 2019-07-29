package lean.file;

import java.io.*;

/**
 * @Description TODO
 * @Author wanghao@cncloudsec.com
 * @Date 18-12-18 下午4:56
 */
public class FileCopy {

    public static byte[] copy(final String fileName) throws IOException {
        FileInputStream inputStream = new FileInputStream(new File(fileName));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) > -1 ) {
            outputStream.write(buffer, 0, len);
        }
        byte[] bytes = outputStream.toByteArray();
        inputStream.close();
        outputStream.close();
        return bytes;
    }

    public static void writeFile(String fileName, byte[] content) throws IOException {
        File file = new File(fileName);
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(content);
        outputStream.flush();
        outputStream.close();
    }

}

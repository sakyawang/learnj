package lean.file.ftp;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main2 {

    public static void main(String[] args) {
        FTPClient client = new FTPClient();
        String ftpFile = "phpworkspace.zip";
        String localFileName = "F:/my2.zip";
        try {
            client.connect("10.2.0.101");
            client.login("test", "test");
            int replyCode = client.getReplyCode();
            boolean flag = FTPReply.isPositiveCompletion(replyCode);
            if(flag) {
                client.setControlEncoding("UTF-8"); // 中文支持
                client.setFileType(FTPClient.BINARY_FILE_TYPE);
                client.enterLocalPassiveMode();
                client.changeWorkingDirectory(ftpFile);
                File localFile = new File(localFileName);
                if(!localFile.exists()) {
                    localFile.createNewFile();
                }
                InputStream inputStream = client.retrieveFileStream(ftpFile);
                FileOutputStream outputStream = new FileOutputStream(localFile);
                ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
                ZipEntry zipEntry = new ZipEntry(ftpFile);
                zipOutputStream.putNextEntry(zipEntry);
                byte[] buf = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buf)) > 0) {
                    zipOutputStream.write(buf, 0, bytesRead);
                }
                zipOutputStream.closeEntry();
                zipOutputStream.close();
                outputStream.close();
                inputStream.close();
                client.logout();
                ZipFile zipFile = new ZipFile(localFileName);
                zipFile.extractAll("e:/my2");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }
}

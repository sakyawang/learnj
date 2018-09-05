package lean.file.ftp;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Main {

    public static void main(String[] args) {
        FTPClient client = new FTPClient();
        String ftpFile = "phpworkspace.zip";
        String localFileName = "F:/my.zip";
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
                OutputStream os = new FileOutputStream(localFile);
                client.retrieveFile(ftpFile, os);
                os.close();
                client.logout();
                ZipFile zipFile = new ZipFile(localFileName);
                zipFile.extractAll("e:/my");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }
}

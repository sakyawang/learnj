package lean.ssh;

import com.jcraft.jsch.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * @Description TODO
 * @Author wanghao@cncloudsec.com
 * @Date 19-6-29 上午11:11
 */
public class Sudo {

    private static volatile boolean COMMAND_DONE = false;

    public static void runSudoCommand(String user, String password, String host, String command) {

        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        JSch jsch = new JSch();
        Session session;
        try {
            session = jsch.getSession(user, host, 22);
            session.setPassword(password);
            session.setConfig(config);
            session.connect();
            System.out.println("Connected to " + host);
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand("su - root");
            channel.setInputStream(null);
            OutputStream out = channel.getOutputStream();
            ((ChannelExec) channel).setErrStream(System.err);
            InputStream in = channel.getInputStream();
            ((ChannelExec) channel).setPty(true);
            channel.connect();
            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) {
                        break;
                    }
                    String result = new String(tmp, 0, i);
                    if (result.startsWith("Password:")) {
                        out.write((password + "\n").getBytes());
                        out.flush();
                    }
                    if (result.startsWith("[root") && !COMMAND_DONE) {
                        out.write((command + "\n").getBytes());
                        out.flush();
                        Thread.sleep(500);
                        COMMAND_DONE = true;
                    }
                    System.out.print(new String(tmp, 0, i));
                }
                if (COMMAND_DONE) {
                    System.out.println("Exit status: " + channel.getExitStatus());
                    break;
                }
            }
            session.disconnect();
            System.out.println("DONE");
        } catch (JSchException | IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] arg) {

        String user = "test";
        String host = "10.2.0.6";
        String password = "test";
        String command = "ls -l /home";

        runSudoCommand(user, password, host, command);
    }
}

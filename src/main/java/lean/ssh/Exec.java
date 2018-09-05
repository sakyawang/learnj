package lean.ssh;/* -*-mode:java; c-basic-offset:2; indent-tabs-mode:nil -*- */

import com.jcraft.jsch.*;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

public class Exec {
    public static void main(String[] arg) {
        try {
            JSch jsch = new JSch();

            String host = "10.2.5.50";
            String user = "admin";
            Session session = jsch.getSession(user, host, 22);
            // username and password will be given via UserInfo interface.
            LoginUserInfo ui = new LoginUserInfo();
            ui.setPassword("admin");
            session.setUserInfo(ui);
            session.connect();

            String command = "enable\n";

            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);

            // X Forwarding
            // channel.setXForwarding(true);

            //channel.setInputStream(System.in);
            channel.setInputStream(null);

            //channel.setOutputStream(System.out);

            //FileOutputStream fos=new FileOutputStream("/tmp/stderr");
            //((ChannelExec)channel).setErrStream(fos);
            ((ChannelExec) channel).setErrStream(System.err);

            InputStream in = channel.getInputStream();

            channel.connect();

            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) break;
                    System.out.print(new String(tmp, 0, i));
                }
                if (channel.isClosed()) {
                    System.out.println("exit-status: " + channel.getExitStatus());
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception ee) {
                }
            }
            channel.disconnect();
            session.disconnect();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}

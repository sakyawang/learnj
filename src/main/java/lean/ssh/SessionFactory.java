package lean.ssh;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import lean.http.waf.DeviceInfo;
import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class SessionFactory extends BaseKeyedPooledObjectFactory<DeviceInfo, Session> {

    @Override
    public Session create(DeviceInfo deviceInfo) throws Exception {
        JSch jsch = new JSch();
        Session session = jsch.getSession(deviceInfo.getUser(), deviceInfo.getIp(), deviceInfo.getPort());
        LoginUserInfo userInfo = new LoginUserInfo();
        userInfo.setPassword(deviceInfo.getPwd());
        session.setUserInfo(userInfo);
        session.setTimeout(1500);
        session.setPassword(deviceInfo.getPwd());
        session.connect();
        return session;
    }

    @Override
    public PooledObject<Session> wrap(Session session) {
        return null;
    }

    public static void main(String[] args) {
        SessionFactory factory = new SessionFactory();
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setIp("10.2.15.30");
        deviceInfo.setUser("sakyawang");
        deviceInfo.setPwd("Cloudm@p");
        deviceInfo.setPort(22);
        try {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Session session = factory.create(deviceInfo);
            session.sendKeepAliveMsg();
            ChannelExec channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand("sudo service network status");
            channel.setOutputStream(baos);
            channel.connect();
            while (!channel.isClosed()) {
                Thread.sleep(500);
            }
            String result = baos.toString("utf-8");
            String[] split = result.split("\n");
            System.out.println(split.length);
            Arrays.asList(split).forEach(System.out::println);
            baos.close();
            channel.disconnect();
            session.disconnect();
            /*channel.setCommand("ovs-vsctl --columns=name list interface | grep vnet | awk '{print $3}'");
            channel.setOutputStream(baos);
            channel.connect();
            while (!channel.isClosed()) {
                Thread.sleep(500);
            }
            String result = baos.toString("utf-8");
            String[] split = result.split("\n");
            System.out.println(split.length);
            Arrays.asList(split).forEach(System.out::println);
            baos.close();
            channel.disconnect();
            session = factory.create(deviceInfo);
            ChannelExec exec = (ChannelExec) session.openChannel("exec");
            final ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
            exec.setCommand("ovs-vsctl list bridge | grep name | awk '{print $3}'");
            exec.setOutputStream(baos1);
            exec.connect();
            while (!exec.isClosed()) {
                Thread.sleep(1000);
            }
            result = baos1.toString("utf-8");
            System.out.println(result);
            baos1.close();
            exec.disconnect();
            session.disconnect();*/
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

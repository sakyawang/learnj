package lean.ssh;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import lean.http.waf.DeviceInfo;
import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;

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
        deviceInfo.setIp("10.2.5.50");
        deviceInfo.setUser("admin");
        deviceInfo.setPwd("admin");
        deviceInfo.setPort(22);
        try {
            Session session = factory.create(deviceInfo);
            Channel channel = session.openChannel("exec");
            ((ChannelExec)channel).setCommand("enable");
            channel.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

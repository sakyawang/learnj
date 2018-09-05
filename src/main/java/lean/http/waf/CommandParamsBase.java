package lean.http.waf;

import java.io.Serializable;

/**
 * 安全设备操作使用参数基类
 *
 * @author 浩
 */
public class CommandParamsBase implements Serializable {

    /**
     * 设备厂家
     */
    private String vendor;

    /**
     * 设备类型
     */
    private String type;

    /**
     * 设备版本
     */
    private String version;

    /**
     * 安全设备管理IP
     */
    private String ip;

    /**
     * 安全设备管理端口
     */
    private int port;

    /**
     * 登录用户名
     */
    private String userName;

    /**
     * 登陆密码
     */
    private String password;

    /**
     * 请求协议
     */
    private String protocol;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}

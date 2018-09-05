package lean.ssh;

import com.jcraft.jsch.UserInfo;

public class LoginUserInfo implements UserInfo {

    private String password;

    @Override
    public String getPassphrase() {
        return password;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean promptPassword(String s) {
        return true;
    }

    @Override
    public boolean promptPassphrase(String s) {
        return true;
    }

    @Override
    public boolean promptYesNo(String s) {
        return true;
    }

    @Override
    public void showMessage(String s) {

    }

    public void setPassword(String password) {
        this.password = password;
    }
}

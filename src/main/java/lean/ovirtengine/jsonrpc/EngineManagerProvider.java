package lean.ovirtengine.jsonrpc;


import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import org.ovirt.vdsm.jsonrpc.client.reactors.ManagerProvider;

public class EngineManagerProvider extends ManagerProvider {
    private String sslProtocol;

    public EngineManagerProvider(String sslProtocol) {
        this.sslProtocol = sslProtocol;
    }

    public KeyManager[] getKeyManagers() throws GeneralSecurityException {
        return EngineEncryptionUtils.getKeyManagers();
    }

    public TrustManager[] getTrustManagers() throws GeneralSecurityException {
        return EngineEncryptionUtils.getTrustManagers();
    }

    public SSLContext getSSLContext() throws GeneralSecurityException {
        try {
            SSLContext context = SSLContext.getInstance(this.sslProtocol);
            context.init(this.getKeyManagers(), this.getTrustManagers(), (SecureRandom)null);
            return context;
        } catch (NoSuchAlgorithmException | KeyManagementException var3) {
            throw new RuntimeException(var3);
        }
    }
}

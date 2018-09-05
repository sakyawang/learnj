package lean.ovirtengine.jsonrpc;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.crypto.Cipher;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import org.apache.commons.codec.binary.Base64;
import org.ovirt.engine.core.common.config.Config;
import org.ovirt.engine.core.common.config.ConfigValues;
import org.ovirt.engine.core.utils.EngineLocalConfig;
import org.ovirt.engine.core.uutils.ssh.OpenSSHUtils;

public class EngineEncryptionUtils {
    private static final String keystoreType;
    private static final File keystoreFile;
    private static final PasswordProtection keystorePassword;
    private static final String keystoreAlias;
    private static final String truststoreType;
    private static final File truststoreFile;
    private static final PasswordProtection truststorePassword;

    public EngineEncryptionUtils() {
    }

    private static KeyStore getKeyStore(String type, File file, char[] password) {
        try {
            InputStream in = new FileInputStream(file);
            Throwable var4 = null;

            KeyStore var6;
            try {
                KeyStore ks = KeyStore.getInstance(type);
                ks.load(in, password);
                var6 = ks;
            } catch (Throwable var16) {
                var4 = var16;
                throw var16;
            } finally {
                if (in != null) {
                    if (var4 != null) {
                        try {
                            in.close();
                        } catch (Throwable var15) {
                            var4.addSuppressed(var15);
                        }
                    } else {
                        in.close();
                    }
                }

            }

            return var6;
        } catch (Exception var18) {
            throw new RuntimeException(String.format("Failed to local keystore '%1$s'", file), var18);
        }
    }

    public static KeyStore getKeyStore() {
        return getKeyStore(keystoreType, keystoreFile, keystorePassword.getPassword());
    }

    public static KeyStore getTrustStore() {
        return getKeyStore(truststoreType, truststoreFile, truststorePassword.getPassword());
    }

    public static PrivateKeyEntry getPrivateKeyEntry() {
        try {
            PrivateKeyEntry entry = (PrivateKeyEntry)getKeyStore().getEntry(keystoreAlias, keystorePassword);
            if (entry == null) {
                throw new RuntimeException("Alias was not found");
            } else {
                return entry;
            }
        } catch (Exception var1) {
            throw new RuntimeException(String.format("Failed to locate key '%1$s'", keystoreAlias), var1);
        }
    }

    public static Certificate getCertificate() {
        return getPrivateKeyEntry().getCertificate();
    }

    public static X509Certificate getCertificate(File file) {
        try {
            InputStream in = new FileInputStream(file);
            Throwable var2 = null;

            X509Certificate var3;
            try {
                var3 = (X509Certificate)CertificateFactory.getInstance("X.509").generateCertificate(in);
            } catch (Throwable var13) {
                var2 = var13;
                throw var13;
            } finally {
                if (in != null) {
                    if (var2 != null) {
                        try {
                            in.close();
                        } catch (Throwable var12) {
                            var2.addSuppressed(var12);
                        }
                    } else {
                        in.close();
                    }
                }

            }

            return var3;
        } catch (IOException | CertificateException var15) {
            throw new RuntimeException(String.format("Failed to read certificate '%1$s'", file.getName()), var15);
        }
    }

    public static String getEngineSSHPublicKey() {
        return OpenSSHUtils.getKeyString(getCertificate().getPublicKey(), (String)Config.getValue(ConfigValues.SSHKeyAlias));
    }

    public static String encrypt(String source) throws GeneralSecurityException {
        if (source != null && source.length() != 0) {
            Cipher rsa = Cipher.getInstance("RSA");
            rsa.init(1, getCertificate().getPublicKey());
            return (new Base64(0)).encodeToString(rsa.doFinal(source.getBytes(StandardCharsets.UTF_8)));
        } else {
            return source;
        }
    }

    public static String decrypt(String source) throws GeneralSecurityException {
        if (source != null && source.length() != 0) {
            Cipher rsa = Cipher.getInstance("RSA");
            rsa.init(2, getPrivateKeyEntry().getPrivateKey());
            return new String(rsa.doFinal((new Base64()).decode(source)), StandardCharsets.UTF_8);
        } else {
            return source;
        }
    }

    public static KeyManager[] getKeyManagers() throws GeneralSecurityException {
        KeyManagerFactory kmfactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmfactory.init(getKeyStore(), keystorePassword.getPassword());
        return kmfactory.getKeyManagers();
    }

    public static TrustManager[] getTrustManagers() throws GeneralSecurityException {
        TrustManagerFactory tmfactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmfactory.init(getTrustStore());
        return tmfactory.getTrustManagers();
    }

    public static boolean haveKey() {
        try {
            getPrivateKeyEntry();
            return true;
        } catch (Exception var1) {
            return false;
        }
    }

    static {
        EngineLocalConfig config = EngineLocalConfig.getInstance();
        keystoreType = "PKCS12";//config.getPKIEngineStoreType();
        keystoreFile = new File("E:\\environment\\ovirt-engine\\keys\\engine.p12");//config.getPKIEngineStore().getAbsoluteFile();
        keystorePassword = new PasswordProtection(/*config.getPKIEngineStorePassword()*/"mypass".toCharArray());
        keystoreAlias = "1";//config.getPKIEngineStoreAlias();
        truststoreType = "JKS";//config.getPKITrustStoreType();
        truststoreFile = new File("E:\\environment\\ovirt-engine\\truststore");// config.getPKITrustStore().getAbsoluteFile();
        truststorePassword = new PasswordProtection(/*config.getPKITrustStorePassword()*/"mypass".toCharArray());
    }
}

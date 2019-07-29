package lean.secret;

import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.DigestInputStream;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.List;
import java.util.zip.InflaterOutputStream;

/**
 * @Description TODO
 * @Author wanghao@cncloudsec.com
 * @Date 19-5-16 下午3:19
 */
public class SecretUtil {

    public static final String TYPE_AES = "aes";
    public static final String TYPE_DES = "des";
    public static final String HMAC_MD5 = "HmacMD5";
    public static final String HMAC_SHA1 = "HmacSha1";
    public static final String HMAC_SHA256 = "HmacSha256";
    public static final String SHA256 = "sha-256";
    public static final String SHA1 = "sha-1";
    public static final String SHA512 = "sha-512";
    public static final String SHA384 = "sha-384";
    public static final String WEB_TASK_KEY = "asywebtask123~!@";

    public static String digestSha1(String str, String type) throws Exception {
        MessageDigest md = MessageDigest.getInstance(type);
        md.update(str.getBytes());
        return toHexString(md.digest());
    }

    public static byte[] digestSha1Byte(String str, String type) throws Exception {
        MessageDigest md = MessageDigest.getInstance(type);
        md.update(str.getBytes());
        return md.digest();
    }

    public static String digestFile(File file, String type) throws Exception {
        InputStream is = null;
        DigestInputStream dis = null;
        MessageDigest md = MessageDigest.getInstance(type);
        try {
            is = new FileInputStream(file);
            dis = new DigestInputStream(is, md);
            byte[] buf = new byte[1024];
            do {
            } while (dis.read(buf) != -1);
        } finally {

            IOUtils.closeQuietly(dis);
            IOUtils.closeQuietly(is);
        }
        return toHexString(md.digest());
    }


    public static byte[] symmetryEncrypt(String type, byte[] content, byte[] keys) throws Exception {
        Cipher encryptCipher = Cipher.getInstance(type);
        encryptCipher.init(1, getKey(keys, type));
        return encryptCipher.doFinal(content);
    }


    public static byte[] decryptEncrypt(String type, byte[] content, byte[] keys) throws Exception {
        Cipher decryptCipher = Cipher.getInstance(type);
        decryptCipher.init(2, getKey(keys, type));
        return decryptCipher.doFinal(content);
    }


    public static void symmetryEncryptFile(File file, File destFile, String type, String keys) throws Exception {
        InputStream is = new FileInputStream(file);
        OutputStream out = new FileOutputStream(destFile);

        Cipher cipherEncrypt = Cipher.getInstance(type);
        cipherEncrypt.init(1, getKey(keys.getBytes("GBK"), type));
        CipherInputStream cis = new CipherInputStream(is, cipherEncrypt);
        byte[] buffer = new byte[1024];
        int r;
        while ((r = cis.read(buffer)) > 0) {
            out.write(buffer, 0, r);
        }
        cis.close();
        is.close();
        out.close();
    }


    public static void decryptEncryptFile(File file, File destFile, String type, String keys) throws Exception {
        InputStream is = new FileInputStream(file);
        OutputStream out = new FileOutputStream(destFile);
        Cipher cipherEncrypt = Cipher.getInstance(type);
        cipherEncrypt.init(2, getKey(keys.getBytes(), type));

        CipherInputStream cis = new CipherInputStream(is, cipherEncrypt);
        byte[] buffer = new byte[1024];
        int r;
        while ((r = cis.read(buffer)) > 0) {
            out.write(buffer, 0, r);
        }
        cis.close();
        is.close();
        out.close();
    }


    public static byte[] rc4Base(byte[] input, String mKkey) {
        int x = 0;
        int y = 0;
        byte[] key = initKey(mKkey);

        byte[] result = new byte[input.length];

        for (int i = 0; i < input.length; i++) {
            x = x + 1 & 0xFF;
            y = (key[x] & 0xFF) + y & 0xFF;
            byte tmp = key[x];
            key[x] = key[y];
            key[y] = tmp;
            int xorIndex = (key[x] & 0xFF) + (key[y] & 0xFF) & 0xFF;
            result[i] = (byte) (input[i] ^ key[xorIndex]);
        }
        return result;
    }


    public static String toHexString(byte[] pb) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < pb.length; i++) {
            int val = pb[i] & 0xFF;
            if (val < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString();
    }


    public static byte[] toBytes(String hexString) {
        int size = hexString.length();
        byte[] ret = new byte[size / 2];
        byte[] tmp = hexString.getBytes();
        for (int i = 0; i < size / 2; i++) {
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
        }
        return ret;
    }


    public static byte[] base64Decoder(String s) throws IOException {
        if (StringUtils.isBlank(s) || "eJwDAAAAAAE=".equalsIgnoreCase(s)) {
            return null;
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        InflaterOutputStream zos = new InflaterOutputStream(bos);
        zos.write((new BASE64Decoder()).decodeBuffer(s));
        zos.close();
        return bos.toByteArray();
    }


    public static String encryptHMAC(String type, byte[] content, byte[] keys) throws Exception {
        SecretKey secretKey = new SecretKeySpec(keys, type);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return toHexString(mac.doFinal(content));
    }

    private static byte uniteBytes(byte src0, byte src1) {
        int _b0 = (char) Byte.decode("0x" + new String(new byte[]{src0})).byteValue();
        _b0 = (char) (_b0 << '\004');
        int _b1 = (char) Byte.decode("0x" + new String(new byte[]{src1})).byteValue();
        return (byte) (_b0 ^ _b1);
    }


    private static byte[] initKey(String aKey) {
        byte[] b_key = aKey.getBytes();
        byte[] state = new byte[256];

        for (int i = 0; i < 256; i++) {
            state[i] = (byte) i;
        }
        int index1 = 0;
        int index2 = 0;
        if (b_key == null || b_key.length == 0) {
            return null;
        }
        for (int i = 0; i < 256; i++) {
            index2 = (b_key[index1] & 0xFF) + (state[i] & 0xFF) + index2 & 0xFF;
            byte tmp = state[i];
            state[i] = state[index2];
            state[index2] = tmp;
            index1 = (index1 + 1) % b_key.length;
        }
        return state;
    }


    private static Key getKey(byte[] keyBytes, String type) {
        int len = "des".equalsIgnoreCase(type) ? 8 : 16;
        byte[] array = new byte[len];
        System.arraycopy(keyBytes, 0, array, 0, Math.min(keyBytes.length, len));
        return new SecretKeySpec(array, type);
    }


    public static void main(String[] args) throws Exception {
        List<String> paramList = Lists.newArrayList("id=7");
        Collections.sort(paramList);
        String params = StringUtils.join(paramList, "&");
        String paramToken = encryptHMAC("HmacSha256", params.getBytes("UTF-8"),
                "asywebtask123~!@".getBytes("UTF-8"));
        System.out.println(paramToken);
    }
}

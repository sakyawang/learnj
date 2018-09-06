package lean.algorithm.base;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class BaseMd {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String moduleString = Base64.encode("site_policy".getBytes());
        System.out.println(moduleString);
        String dataString = URLEncoder.encode("{}", "UTF-8");
        System.out.println(dataString);
        dataString = Base64.encode(dataString.getBytes());
        System.out.println(dataString);
        String time = System.currentTimeMillis()+"";
    }
}

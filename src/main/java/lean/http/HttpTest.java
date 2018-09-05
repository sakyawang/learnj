package lean.http;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.CharArrayBuffer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Created by IntelliJ IDEA.
 * User: wanghao@weipass.cn
 * Date: 2016/5/18
 * Time: 15:59
 */
public class HttpTest {

    public static String get(String url) throws IOException {

        return connect("GET",url);
    }

    public static String post(String url) throws IOException {

        return connect("POST",url);
    }

    public static String put(String url) throws IOException {

        return connect("PUT",url);
    }

    public static String delete(String url) throws IOException {

        return connect("DELETE",url);
    }

    public static String connect(String method,String url) throws IOException {

        HttpUriRequest request = null;
        switch (method){
            case "GET":
                request = new HttpGet(url);
                break;
            case "POST":
                request = new HttpPost(url);
                break;
            case "PUT":
                request = new HttpPut(url);
                break;
            case "DELETE":
                request = new HttpDelete(url);
                break;
            default:
                request = new HttpGet(url);
        }
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        String result = "";
        if (entity != null) {
            InputStream content = entity.getContent();
            InputStreamReader reader = new InputStreamReader(content, Charset.forName("utf-8"));
            int i = (int)entity.getContentLength();
            if(i < 0) {
                i = 4096;
            }
            CharArrayBuffer buffer = new CharArrayBuffer(i);
            char[] tmp = new char[1024];
            int len;
            while((len = reader.read(tmp)) != -1) {
                buffer.append(tmp, 0, len);
            }
            result = buffer.toString();
        }
        return result;
    }
    public static void main(String[] args) throws IOException {

        String url = "http://29.onpos.cn/sakura/api/user.xhtml";
        String result = get(url);
        System.out.println(result);
        result = post(url);
        System.out.println(result);
        result = put(url);
        System.out.println(result);
        result = delete(url);
        System.out.println(result);
    }
}

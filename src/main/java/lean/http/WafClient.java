package lean.http;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WafClient {

    private static volatile Map<String, HttpClientContext> ContextMap = new ConcurrentHashMap<>();

    private static final String AUTH_URL = "http://%s/api.php/v1/auth/authorization";

    private static HttpClientContext getContext(String ip) throws IOException {
        HttpClientContext context = ContextMap.get(ip);
        if(null == context) {
            synchronized (WafClient.class) {
                if(null == context) {
                    context = initContext(ip);
                    ContextMap.put(ip, context);
                }
            }
        }
        return context;
    }

    private static HttpClientContext initContext(String ip) {
        HttpClientContext context = new HttpClientContext();
        BasicCookieStore cookieStore = new BasicCookieStore();
        context.setCookieStore(cookieStore);
        HttpPost post = new HttpPost(String.format(AUTH_URL, ip));
        ArrayList<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("username", "adm"));
        nvps.add(new BasicNameValuePair("password", "dcsec110"));
        nvps.add(new BasicNameValuePair("checkCode", "no-check-code"));
        CloseableHttpResponse response = null;
        CloseableHttpClient client = null;
        try {
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvps);
            post.setEntity(formEntity);
            client = HttpClients.createDefault();
            response = client.execute(post);
            HttpEntity entity1 = response.getEntity();
            if (entity1 != null) {
                EntityUtils.consume(entity1);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return context;
    }

    public static JSONArray getDomainList(String ip) throws IOException {

        ArrayList<NameValuePair> nvps = new ArrayList<>();
        String module = "site_policy";
        String head = generalHead(module);
        nvps.add(new BasicNameValuePair("head", head));
        String bodyJson = "{}";
        String body = generalBody(bodyJson);
        nvps.add(new BasicNameValuePair("body", body));
        String time = System.currentTimeMillis()+"";
        nvps.add(new BasicNameValuePair("time", time));
        nvps.add(new BasicNameValuePair("sign", generalSign(head, body, time)));

        HttpPost post = new HttpPost("http://"+ip+"/api.php/v1/comm/show");
        post.setEntity(new UrlEncodedFormEntity(nvps));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpClientContext context = getContext(ip);
        CloseableHttpResponse response = httpClient.execute(post, context);
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
        JSONObject parse = (JSONObject) JSONObject.parse(result);
        return parse.getJSONArray("data");
    }

    private static String generalHead(String module) {
        return Base64.encode(module.getBytes());
    }

    private static String generalBody(String bodyJson) throws UnsupportedEncodingException {
        return Base64.encode(URLEncoder.encode(bodyJson, "UTF-8").getBytes());
    }

    private static String generalSign(String head, String body, String time) {
        String sign = new StringBuilder().append(head).append(".").append(body).append(".").append(time).toString();
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return String.valueOf(md5.digest(sign.getBytes()));
    }

    public static JSONObject addDomain(String ip, String name, String domain, String filePath) throws IOException {
        HttpPost request = new HttpPost("http://192.168.0.246/api.php/v1/comm/add");
        CloseableHttpClient client = HttpClients.createDefault();

        List<NameValuePair> nvps = new ArrayList<>();
        String module = "site_policy";
        String head = generalHead(module);
        nvps.add(new BasicNameValuePair("head", head));

        JSONObject bodyJson = new JSONObject();
        bodyJson.put("name", name);
        bodyJson.put("domain", domain);
        bodyJson.put("file_path", null == filePath ? "": filePath);
        String body = generalBody(bodyJson.toJSONString());
        nvps.add(new BasicNameValuePair("body", body));

        String time = System.currentTimeMillis()+"";
        nvps.add(new BasicNameValuePair("time", time));

        nvps.add(new BasicNameValuePair("sign", generalSign(head, body, time)));

        request.setEntity(new UrlEncodedFormEntity(nvps));
        HttpClientContext context = getContext(ip);
        CloseableHttpResponse response = client.execute(request, context);
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
        JSONObject parse = (JSONObject) JSONObject.parse(result);
        return parse;
    }

    /**
     * 名称和域名作为查询条件进行修改的
     * @param ip
     * @param name
     * @param domain
     * @param filePath
     * @return
     * @throws IOException
     */
    public static JSONObject modifyDomain(String ip, String name, String domain, String filePath) throws IOException {
        HttpPost request = new HttpPost("http://192.168.0.246/api.php/v1/comm/modify");
        CloseableHttpClient client = HttpClients.createDefault();

        List<NameValuePair> nvps = new ArrayList<>();
        String module = "site_policy";
        String head = generalHead(module);
        nvps.add(new BasicNameValuePair("head", head));

        JSONObject bodyJson = new JSONObject();
        bodyJson.put("name", name);
        bodyJson.put("domain", domain);
        bodyJson.put("file_path", null == filePath ? "": filePath);
        String body = generalBody(bodyJson.toJSONString());
        nvps.add(new BasicNameValuePair("body", body));

        String time = System.currentTimeMillis()+"";
        nvps.add(new BasicNameValuePair("time", time));

        nvps.add(new BasicNameValuePair("sign", generalSign(head, body, time)));

        request.setEntity(new UrlEncodedFormEntity(nvps));
        HttpClientContext context = getContext(ip);
        CloseableHttpResponse response = client.execute(request, context);
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
        JSONObject parse = (JSONObject) JSONObject.parse(result);
        return parse;
    }

    /**
     * 名称作为查询条件进行删除
     * @param ip
     * @param name
     * @return
     * @throws IOException
     */
    public static JSONObject deleteDomain(String ip, String name) throws IOException {
        HttpPost request = new HttpPost("http://192.168.0.246/api.php/v1/comm/delete");
        CloseableHttpClient client = HttpClients.createDefault();

        List<NameValuePair> nvps = new ArrayList<>();
        String module = "site_policy";
        String head = generalHead(module);
        nvps.add(new BasicNameValuePair("head", head));

        JSONObject bodyJson = new JSONObject();
        bodyJson.put("name", name);
        String body = generalBody(bodyJson.toJSONString());
        nvps.add(new BasicNameValuePair("body", body));

        String time = System.currentTimeMillis()+"";
        nvps.add(new BasicNameValuePair("time", time));

        nvps.add(new BasicNameValuePair("sign", generalSign(head, body, time)));

        request.setEntity(new UrlEncodedFormEntity(nvps));
        HttpClientContext context = getContext(ip);
        CloseableHttpResponse response = client.execute(request, context);
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
        JSONObject parse = (JSONObject) JSONObject.parse(result);
        return parse;
    }

    private static void test() throws IOException {
        HttpPost request = new HttpPost("http://10.2.6.50/api.php/v1/comm/show2");
        CloseableHttpClient client = HttpClients.createDefault();

        List<NameValuePair> nvps = new ArrayList<>();
        String module = "anti_escape";
        String head = generalHead(module);
        nvps.add(new BasicNameValuePair("head", head));

        JSONObject bodyJson = new JSONObject();
        bodyJson.put("profile", "default");
        String body = generalBody(bodyJson.toJSONString());
        nvps.add(new BasicNameValuePair("body", body));

        String time = System.currentTimeMillis()+"";
        nvps.add(new BasicNameValuePair("time", time));

        nvps.add(new BasicNameValuePair("sign", generalSign(head, body, time)));

        request.setEntity(new UrlEncodedFormEntity(nvps));
        HttpClientContext context = getContext("10.2.6.50");
        CloseableHttpResponse response = client.execute(request, context);
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
        JSONObject parse = (JSONObject) JSONObject.parse(result);
        System.out.println(parse.toJSONString());
    }

    public static void main(String[] args) throws IOException {

        String ip = "10.2.6.50";
       /* JSONObject result = addDomain(ip, "测试", "baidu.com", null);
        System.out.println(result.toJSONString());

        JSONObject domain = modifyDomain(ip, "test2", "sakyawang.com", null);
        System.out.println(domain.toJSONString());
        */
        JSONArray domainList = getDomainList(ip);
        System.out.println(domainList.toJSONString());
      /*  JSONObject result = addDomain(ip, "测试", "baidu.com", null);
        System.out.println(result.toJSONString());*/
//        JSONObject result = deleteDomain(ip, "test");
//        System.out.println(result.toJSONString());
       /* domainList = getDomainList(ip);
        System.out.println(domainList.toJSONString());*/
        /*String module = "site_policy";
        String head = generalHead(module);
        JSONObject bodyJson = new JSONObject();
        bodyJson.put("name", "测试");
        bodyJson.put("domain", "test.com");
        bodyJson.put("file_path", "");
        String body = generalBody(bodyJson.toJSONString());
        String time = "1526903506501";
        String sign = generalSign(head, body, time);

        System.out.println(head);
        System.out.println(body);
        System.out.println(sign);*/
        test();

    }


}

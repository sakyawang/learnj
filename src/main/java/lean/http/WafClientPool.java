package lean.http;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import com.wzqj.compoent.encrypt.MD5;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class WafClientPool {

    private static final int COOKIE_SESSION_TIMEOUT = 5;

    private static volatile Cache<String, CloseableHttpClient> clientCache = CacheBuilder.newBuilder()
            .expireAfterWrite(COOKIE_SESSION_TIMEOUT, TimeUnit.MINUTES).build();

    private static final String AUTH_URL = "http://%s/api.php/v1/auth/authorization";

    public static CloseableHttpClient getClient(String host) throws IOException {
        CloseableHttpClient client = clientCache.getIfPresent(host);
        if(null == client) {
            synchronized (WafClientPool.class) {
                if(null == client) {
                    client = initClient(host);
                    clientCache.put(host, client);
                }
            }
        }
        return client;
    }

    private static CloseableHttpClient initClient(String host) throws IOException {
        HttpClientContext context = new HttpClientContext();
        BasicCookieStore cookieStore = new BasicCookieStore();
        context.setCookieStore(cookieStore);
        HttpPost post = new HttpPost(String.format(AUTH_URL, host));
        ArrayList<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("username", "adm"));
        nvps.add(new BasicNameValuePair("password", "dcsec110"));
        nvps.add(new BasicNameValuePair("checkCode", "no-check-code"));
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvps);
        post.setEntity(formEntity);
        CloseableHttpClient client = HttpClients.createDefault();
        post.setHeader("Keep-Alive","timeout=10, max=500");
        post.setHeader("Connection", "Keep-Alive");
        post.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
        CloseableHttpResponse response = client.execute(post, context);
        EntityUtils.consume(response.getEntity());
        return client;
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

        HttpClient client = getClient(ip);
        HttpResponse response = client.execute(post);
        HttpEntity entity = response.getEntity();
//        String result = EntityUtils.toString(entity);
        JSONObject parse = (JSONObject) JSONObject.parse("{}");
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
        return MD5.sign(sign);
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
        return new JSONObject();
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

        JSONObject parse = (JSONObject) JSONObject.parse("{}");
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

        JSONObject parse = (JSONObject) JSONObject.parse("{}");
        return parse;
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
        /*CloseableHttpClient client = getClient(ip);
        client.close();*/
        /*JSONObject result = addDomain(ip, "测试", "baidu.com", null);
        System.out.println(result.toJSONString());*/
//        JSONObject result = deleteDomain(ip, "test");
//        System.out.println(result.toJSONString());
/*        domainList = getDomainList(ip);
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

    }


}

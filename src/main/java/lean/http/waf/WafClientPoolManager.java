package lean.http.waf;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.SocketConfig;
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
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class WafClientPoolManager {

    private static final int COOKIE_SESSION_TIMEOUT = 5;

    private static volatile Cache<String, HttpClientContext> contextCache = CacheBuilder.newBuilder()
            .expireAfterWrite(COOKIE_SESSION_TIMEOUT, TimeUnit.MINUTES).build();

    private static volatile PoolingHttpClientConnectionManager cm = null;

    private static final String AUTH_URL = "%s://%s:%d/api.php/v1/auth/authorization";

    static {
        cm = new PoolingHttpClientConnectionManager();
        //最大连接数
        cm.setMaxTotal(200);
        //默认的每个路由的最大连接数
        cm.setDefaultMaxPerRoute(20);
        /**
         * socket配置（默认配置 和 某个host的配置）
         */
        SocketConfig socketConfig = SocketConfig.custom()
                .setTcpNoDelay(true)     //是否立即发送数据，设置为true会关闭Socket缓冲，默认为false
                .setSoReuseAddress(true) //是否可以在一个进程关闭Socket后，即使它还没有释放端口，其它进程还可以立即重用端口
                .setSoTimeout(500)       //接收数据的等待超时时间，单位ms
                .setSoLinger(60)         //关闭Socket时，要么发送完所有数据，要么等待60s后，就关闭连接，此时socket.close()是阻塞的
                .setSoKeepAlive(true)    //开启监视TCP连接是否有效
                .build();
        cm.setDefaultSocketConfig(socketConfig);
    }

    public static WafClient getClient(WafRequest request) throws IOException {
        HttpClientContext context = getContext(request.getProtocol(), request.getIp(), request.getPort(),
                request.getUserName(), request.getPassword());
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(cm).evictExpiredConnections().build();
        return new WafClient(context, client, request);
    }

    private static HttpClientContext getContext(String protocol, String ip, int port, String userName, String passWord) throws IOException {
        HttpClientContext context = contextCache.getIfPresent(ip);
        if(null == context) {
            synchronized (WafClientPoolManager.class) {
                if(null == context) {
                    context = initContext(protocol, ip, port, userName, passWord);
                    contextCache.put(ip, context);
                }
            }
        }
        return context;
    }

    private static HttpClientContext initContext(String protocol, String ip, int port, String userName, String passWord) throws IOException {
        HttpClientContext context = new HttpClientContext();
        BasicCookieStore cookieStore = new BasicCookieStore();
        context.setCookieStore(cookieStore);
        HttpPost post = new HttpPost(String.format(AUTH_URL, protocol, ip, port));
        ArrayList<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("username", userName));
        nvps.add(new BasicNameValuePair("password", passWord));
        nvps.add(new BasicNameValuePair("checkCode", "no-check-code"));
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvps);
        post.setEntity(formEntity);
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(cm).build();
        CloseableHttpResponse response = client.execute(post, context);
        HttpEntity entity = response.getEntity();
        EntityUtils.consume(entity);
        return context;
    }

}

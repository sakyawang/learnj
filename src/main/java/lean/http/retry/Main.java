package lean.http.retry;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @Description TODO
 * @Author wanghao@cncloudsec.com
 * @Date 19-3-27 下午4:47
 */
public class Main {

    public static void main(String[] args) throws IOException {
        CustomRetryHandler customRetryHandler = new CustomRetryHandler();
        CloseableHttpClient httpClient = HttpClients.custom().setServiceUnavailableRetryStrategy(customRetryHandler).build();
        HttpGet httpGet = new HttpGet("http://10.2.6.17/dsadas");
        CloseableHttpResponse response = httpClient.execute(httpGet);
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        HttpEntity entity = response.getEntity();
        String content = EntityUtils.toString(entity);
        System.out.println(content);
    }
}

package lean.http.waf;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;

public class WafClient implements HttpClient {

    private CloseableHttpClient client;

    private HttpClientContext context;

    private WafRequest request;

    public WafClient(HttpClientContext context, CloseableHttpClient client, WafRequest request) throws IOException {
        this.client = client;
        this.context = context;
        this.request = request;
    }

    @Override
    public CloseableHttpResponse execute() throws Exception {
        return client.execute(this.request.toHttpRequest(), this.context);
    }

    @Override
    public void close() throws Exception {
        client.close();
    }
}

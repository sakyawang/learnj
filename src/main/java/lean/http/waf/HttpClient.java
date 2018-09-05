package lean.http.waf;

import org.apache.http.client.methods.CloseableHttpResponse;

public interface HttpClient extends IClient {
    /**
     * This method will send http request
     *
     * @return response returned by host
     */
     CloseableHttpResponse execute() throws Exception;

    /**
     * Close http connection
     *
     * @throws Exception
     */
    void close() throws Exception;
}

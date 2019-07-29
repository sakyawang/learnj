package lean.http.retry;

import org.apache.http.HttpResponse;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.protocol.HttpContext;

/**
 * @Description TODO
 * @Author wanghao@cncloudsec.com
 * @Date 19-3-27 下午4:52
 */
public class CustomRetryHandler implements ServiceUnavailableRetryStrategy {

    private final int maxRetries;
    private final long retryInterval;
    private volatile int currentRetryNum;

    public CustomRetryHandler(int maxRetries, long retryInterval) {
        this.maxRetries = maxRetries;
        this.retryInterval = retryInterval;
    }

    public CustomRetryHandler() {
        this(4, 3000);
    }

    @Override
    public boolean retryRequest(HttpResponse httpResponse, int executionCount, HttpContext httpContext) {
        System.out.println("exception count is: " + executionCount);
        this.currentRetryNum = executionCount++;
        return executionCount <= this.maxRetries && httpResponse.getStatusLine().getStatusCode() >= 400;
    }

    @Override
    public long getRetryInterval() {
        if(currentRetryNum == 0) {
            return 3 * 1000;
        } else if (currentRetryNum == 1) {
            return 30 * 1000;
        } else {
            return 100 * 1000;
        }
    }

}

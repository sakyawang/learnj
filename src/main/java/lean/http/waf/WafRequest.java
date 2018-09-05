package lean.http.waf;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class WafRequest extends CommandParamsBase {

    private static final String REQUEST_HEAD_KEY = "head";

    private static final String REQUEST_BODY_KEY = "body";

    private static final String REQUEST_TIME_KEY = "time";

    private static final String REQUEST_SIGN_KEY = "sign";

    private static final String DEFAULT_CHARSETNAME = "UTF-8";

    private static final String SIGN_RANGE_SEPARATOR = ".";

    private static final String FULL_REQUEST_URL_FORMAT = "%s://%s:%d/%s";

    private static final String PROTOCOL_HTTP = "http";

    private static final String PROTOCOL_HTTPS = "https";

    private static final int MINIMUM_PORT = 0;

    private static final int MAXIMUM_PORT = 65535;

    private static final int DEFAULT_HTTP_PORT = 80;

    private static final int DEFAULT_HTTPS_PORT = 443;

    private WafRequestType requestType;

    private String jsonBody;

    public WafRequest(WafRequestType type, String jsonBody, DeviceInfo deviceInfo) {
        this.jsonBody = jsonBody;
        this.requestType = type;
        this.setIp(deviceInfo.getIp());
        String protocol = deviceInfo.getProtocol();
        if(null == protocol || !PROTOCOL_HTTP.equals(protocol) || !PROTOCOL_HTTPS.equals(protocol)) {
            protocol = PROTOCOL_HTTP;
        }
        this.setProtocol(protocol);
        int port = deviceInfo.getPort();
        if(port <= MINIMUM_PORT || port > MAXIMUM_PORT) {
            if(PROTOCOL_HTTP.equals(protocol)) {
                port = DEFAULT_HTTP_PORT;
            } else {
                port = DEFAULT_HTTPS_PORT;
            }
        }
        this.setPort(port);
        this.setUserName(deviceInfo.getUser());
        this.setPassword(deviceInfo.getPwd());
    }

    public HttpUriRequest toHttpRequest() throws UnsupportedEncodingException {

        ArrayList<NameValuePair> nvps = new ArrayList<>();
        String head = generalHead();
        nvps.add(new BasicNameValuePair(REQUEST_HEAD_KEY, head));
        String body = generalBody();
        nvps.add(new BasicNameValuePair(REQUEST_BODY_KEY, body));
        String time = System.currentTimeMillis()+"";
        nvps.add(new BasicNameValuePair(REQUEST_TIME_KEY, time));
        nvps.add(new BasicNameValuePair(REQUEST_SIGN_KEY, generalSign(head, body, time)));
        String url = String.format(FULL_REQUEST_URL_FORMAT, getProtocol(), getIp(), getPort(), getRequestType().getPath());
        HttpPost post = new HttpPost(url);
        post.setEntity(new UrlEncodedFormEntity(nvps));
        return post;
    }

    private String generalHead() throws UnsupportedEncodingException {
        return Base64.encode(getRequestType().getModule().getBytes(DEFAULT_CHARSETNAME));
    }

    private String generalBody() throws UnsupportedEncodingException {
        return Base64.encode(URLEncoder.encode(getJsonBody(), DEFAULT_CHARSETNAME).getBytes());
    }

    private String generalSign(String head, String body, String time) {
        String sign = new StringBuilder().append(head).append(SIGN_RANGE_SEPARATOR)
                .append(body).append(SIGN_RANGE_SEPARATOR).append(time).toString();
        return DigestUtils.md5Hex(sign);
    }

    public String getJsonBody() {
        return jsonBody;
    }

    public void setJsonBody(String jsonBody) {
        this.jsonBody = jsonBody;
    }

    public WafRequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(WafRequestType requestType) {
        this.requestType = requestType;
    }
}

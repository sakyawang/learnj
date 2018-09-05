package lean.http.waf;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import static lean.http.waf.WafClientPoolManager.getClient;
import static lean.http.waf.WafRequestType.SHOWKEYWORD;
import static lean.http.waf.WafRequestType.SHOWSITE;

public class Main {

    public static void main(String[] args) throws Exception {
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setIp("10.2.6.50");
        deviceInfo.setPort(80);
        deviceInfo.setUser("adm");
        deviceInfo.setPwd("dcsec11");
        deviceInfo.setProtocol("http");
        WafRequest request = new WafRequest(SHOWSITE, "{}", deviceInfo);
        WafClient client = getClient(request);
        CloseableHttpResponse response = client.execute();
        int code = response.getStatusLine().getStatusCode();
        if(code == HttpStatus.SC_OK) {
            HttpEntity entity = response.getEntity();
            String json = EntityUtils.toString(entity);
            JSONObject data = JSON.parseObject(json);
            System.out.println(data.toJSONString());
            EntityUtils.consume(entity);
        }
//        response.close();
        request = new WafRequest(SHOWKEYWORD, "{}", deviceInfo);
        client = getClient(request);
        response = client.execute();
        code = response.getStatusLine().getStatusCode();
        if(code == HttpStatus.SC_OK) {
            HttpEntity entity = response.getEntity();
            String json = EntityUtils.toString(entity);
            JSONObject data = JSON.parseObject(json);
            System.out.println(data.toJSONString());
        }
//        response.close();
    }
}

package lean.socket.client;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by 浩 on 2017/3/9.
 */
public class FlqSocketClientPool {

    private static Map<String, FlqSocketClient> socketClientMap = new ConcurrentHashMap<>();

    public static synchronized FlqSocketClient getClient(String ip, int port) {

        FlqSocketClient client = socketClientMap.get(ip);
        if (null == client) {
            synchronized (FlqSocketClientPool.class){
                client = new FlqSocketClient(ip, port);
                socketClientMap.put(ip, client);
                return client;
            }
        } else if (client.getPort() != port) {
            synchronized (FlqSocketClientPool.class){
                FlqSocketClient flqSocketClient = new FlqSocketClient(ip, port);
                socketClientMap.put(ip, flqSocketClient);
                return flqSocketClient;
            }
        }
        return client;
    }

}
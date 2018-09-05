package lean.socket.client;

import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by 浩 on 2017/3/13.
 */
public class FlqClientFactory {

    private static final Map<String, SocketChannel> channelMap = new ConcurrentHashMap<>();

}

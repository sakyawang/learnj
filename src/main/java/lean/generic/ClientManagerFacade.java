package lean.generic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ClientManagerFacade {

    private static final Logger logger = LoggerFactory.getLogger(ClientManagerFacade.class);

    private static final String GET_CLIENT_METHOD_NAME = "getClient";

    private static ConcurrentMap<String, Class<AbstractClientManager>> clientManagerCache =
            new ConcurrentHashMap<>();

    public static GenericClient getClient(BasePlatformInfo platform) {
        String clientManager = platform.getClientManager();
        Class<AbstractClientManager> clazz = getClientManagerClass(clientManager);
        try {
            Method method = clazz.getMethod(GET_CLIENT_METHOD_NAME, new Class[]{BasePlatformInfo.class});
            IClient client = (IClient) method.invoke(null, platform);
            GenericClient<IClient> genericClient = new GenericClient<>();
            genericClient.setClient(client);
            return genericClient;
        } catch (NoSuchMethodException e) {
            logger.error("ClientManagerFacade#getClient:客户端管理类{}没有符合条件的getClient方法。", clientManager, e);
            throw new RuntimeException("获取客户端失败", e);
        } catch (IllegalAccessException e) {
            logger.error("ClientManagerFacade#getClient:客户端管理类{}初始化失败。", clientManager, e);
            throw new RuntimeException("获取客户端失败", e);
        } catch (InvocationTargetException e) {
            logger.error("ClientManagerFacade#getClient:客户端管理类{}执行getClient方法失败。", clientManager, e);
            throw new RuntimeException("获取客户端失败", e);
        }
    }

    private static Class<AbstractClientManager> getClientManagerClass(String clientManager) {
        Class<AbstractClientManager> clazz = clientManagerCache.get(clientManager);
        if (clazz != null) {
            return clazz;
        }
        Class<AbstractClientManager> type = loadClass(clientManager);
        Class<AbstractClientManager> cachedType = clientManagerCache.putIfAbsent(clientManager, type);
        return cachedType == null ? type : cachedType;
    }

    private static Class<AbstractClientManager> loadClass(String className) {
        try {
            return (Class<AbstractClientManager>) Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.error("ClientManagerFacade#getClient:加载客户端管理类{}失败", className, e);
            throw new RuntimeException("客户端管理类加载失败", e);
        }
    }

    public static void main(String[] args) {
        BasePlatformInfo platformInfo = new BasePlatformInfo();
        platformInfo.setClientManager("lean.generic.ClientTwoManagerImpl");
        GenericClient<ClientTwoImpl> client = ClientManagerFacade.getClient(platformInfo);
        ClientTwoImpl clientOne = client.getClient();
        clientOne.say();
    }
}

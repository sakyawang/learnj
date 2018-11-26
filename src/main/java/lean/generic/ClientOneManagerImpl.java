package lean.generic;

public class ClientOneManagerImpl extends AbstractClientManager<ClientOneImpl> {

    public static ClientOneImpl getClient(BasePlatformInfo platformInfo) {
        return new ClientOneImpl();
    }
}

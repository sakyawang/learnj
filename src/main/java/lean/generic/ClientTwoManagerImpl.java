package lean.generic;

public class ClientTwoManagerImpl extends AbstractClientManager<ClientTwoImpl> {

    public static ClientTwoImpl getClient(BasePlatformInfo platformInfo) {
        return new ClientTwoImpl();
    }
}

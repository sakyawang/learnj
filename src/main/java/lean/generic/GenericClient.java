package lean.generic;

public class GenericClient<T extends IClient> {

    private T client;

    public T getClient() {
        return client;
    }

    public void setClient(T client) {
        this.client = client;
    }
}

package lean.rpc.base;

import java.io.Serializable;

/**
 * Created by 浩 on 2017/5/11.
 */
public class RpcRequest<T extends Serializable> implements Serializable{

    private T params;

    public T getParams() {
        return params;
    }

    public void setParams(T params) {
        this.params = params;
    }
}

package lean.rpc.base;

import java.io.Serializable;

/**
 * Created by 浩 on 2017/5/11.
 */
public abstract class AbstractService<T extends RpcRequest, F extends RpcResponse> implements Serializable {

    public abstract F execute(T request);

}

package lean.rpc.user.service;

import lean.rpc.base.AbstractService;
import lean.rpc.base.RpcRequest;
import lean.rpc.base.RpcResponse;
import lean.rpc.user.po.UserParam;
import lean.rpc.user.po.User;

/**
 * Created by 浩 on 2017/5/11.
 */
public class UserService extends AbstractService<RpcRequest<UserParam>,RpcResponse<User>> {

    @Override
    public RpcResponse<User> execute(RpcRequest<UserParam> request) {
        UserParam params = request.getParams();
        String name = params.getName();
        RpcResponse<User> response = new RpcResponse<>();
        User user = new User();
        if ("wanghao".equals(name)) {
            user.setName("wanghao");
            user.setAge(29);
            response.setResult(user);
        } else {
            response.setCode(-200);
            response.setMessage("no the user");
        }
        return response;
    }
}

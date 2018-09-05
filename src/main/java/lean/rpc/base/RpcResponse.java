package lean.rpc.base;

import java.io.Serializable;

/**
 * Created by 浩 on 2017/5/11.
 */
public class RpcResponse<T extends Serializable> implements Serializable {

    private int code = 200;

    private String message = "success";

    private T result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}

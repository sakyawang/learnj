package lean.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by wanghao@weipass.cn on 2015/7/29.
 */
public class BookFacadeProxy implements InvocationHandler {

    private Object target;

    public BookFacadeProxy(Object target){
        super();
        this.target = target;
    }
    public Object getProxy(){
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),this);
    }
    public Object bind(Object target){
        this.target = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                                        target.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        boolean flag = Proxy.isProxyClass(proxy.getClass());
        System.out.println(flag);
        Object result = null;
        System.out.println("start");
        result = method.invoke(target, args);
        System.out.println("end");
        return result;
    }
}

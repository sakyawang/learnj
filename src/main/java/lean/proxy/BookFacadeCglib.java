package lean.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by wanghao@weipass.cn on 2015/7/29.
 */
public class BookFacadeCglib implements MethodInterceptor {

    private Object target;

    /**
     * 创建代理对象
     *
     * @param target
     * @return
     */
    public Object getInstance(Object target) {
        this.target = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        // 回调方法
        enhancer.setCallback(this);
        // 创建代理对象
        return enhancer.create();
    }

    @Override
    // 回调方法
    public Object intercept(Object Proxy, Method method, Object[] args,
                            MethodProxy proxy) throws Throwable {
        boolean flag = java.lang.reflect.Proxy.isProxyClass(Proxy.getClass());
        System.out.println(flag);
        System.out.println("事物开始");
        proxy.invokeSuper(Proxy, args);
        System.out.println("事物结束");
        return null;
    }

}

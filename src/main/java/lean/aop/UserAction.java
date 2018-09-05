package lean.aop;

import org.nutz.aop.ClassAgent;
import org.nutz.aop.ClassDefiner;
import org.nutz.aop.DefaultClassDefiner;
import org.nutz.aop.asm.AsmClassAgent;
import org.nutz.aop.interceptor.LoggingMethodInterceptor;
import org.nutz.aop.matcher.MethodMatcherFactory;

public class UserAction {

    public void run(String user) throws InterruptedException {
        Thread.sleep(3000);
        System.out.println("come on " + user);
    }

    public static void main(String[] args) throws InterruptedException, IllegalAccessException, InstantiationException {

        UserAction userAction = new UserAction();
        userAction.run("wanghao");

        ClassDefiner cd = DefaultClassDefiner.defaultOne();

        //有AOP的时候
        ClassAgent agent = new AsmClassAgent();
        LogInterceptor log = new LogInterceptor();
        LoggingMethodInterceptor interceptor = new LoggingMethodInterceptor();
        agent.addInterceptor(MethodMatcherFactory.matcher("^run$"), interceptor);
        agent.addInterceptor(MethodMatcherFactory.matcher("^run$"), log);
        //返回被AOP改造的Class实例
        Class<? extends UserAction> userAction2 = agent.define(cd, UserAction.class);
        UserAction action = userAction2.newInstance();
        action.run("wanghao");//通过日志,可以看到方法执行前后有额外的日志
    }
}

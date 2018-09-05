package lean.proxy;

/**
 * Created by wanghao@weipass.cn on 2015/7/29.
 */
public class ProxyTest {

    public static void main(String[] args) {

        BookFacadeStaticProxy staticProxy = new BookFacadeStaticProxy(new BookFacadeImpl());
        staticProxy.addBook();
        BookFacadeProxy proxy = new BookFacadeProxy(new BookFacadeImpl());
//        BookFacade bookProxy = (BookFacade) proxy.bind(new BookFacadeImpl());
        BookFacade bookProxy = (BookFacade) proxy.getProxy();
        bookProxy.addBook();
        BookFacadeCglib cglib = new BookFacadeCglib();
        BookFacadeImpl instance = (BookFacadeImpl) cglib.getInstance(new BookFacadeImpl());
        instance.addBook();
        instance.sayHello();
    }
}

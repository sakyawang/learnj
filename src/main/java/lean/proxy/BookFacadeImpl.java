package lean.proxy;

/**
 * Created by wanghao@weipass.cn on 2015/7/29.
 */
public class BookFacadeImpl implements BookFacade {

    @Override
    public void addBook() {
        System.out.println("添加书籍");
    }

    public void sayHello(){
        System.out.println("hello");
    }
}

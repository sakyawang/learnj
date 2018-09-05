package lean.proxy;

/**
 * Created by IntelliJ IDEA.
 * User: wanghao@weipass.cn
 * Date: 2016/1/29
 * Time: 16:05
 */
public class BookFacadeStaticProxy implements BookFacade{

    private BookFacade bookFacade;

    public BookFacadeStaticProxy(BookFacade bookFacade){

        this.bookFacade = bookFacade;
    }

    @Override
    public void addBook() {
        bookFacade.addBook();
    }
}

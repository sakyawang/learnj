package lean.pattern.observer.jdk;

/**
 * Created by IntelliJ IDEA.
 * User: wanghao@weipass.cn
 * Date: 2016/7/19
 * Time: 13:51
 */
public class Testmain {

    public static void main(String[] args) {
        
        Mytopic mytopic = new Mytopic();
        Myobserver myobserver = new Myobserver();
        mytopic.addObserver(myobserver);
        mytopic.push("hello sdsad");
        myobserver.notify();
    }
}

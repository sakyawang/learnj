package lean.pattern.observer;

/**
 * Created by IntelliJ IDEA.
 * User: wanghao@weipass.cn
 * Date: 2016/7/19
 * Time: 13:28
 */
public class TestMain {

    public static void main(String[] args) {

        MyTopic myTopic = new MyTopic();
        MyObserver myObserver = new MyObserver("wanghao");
        myTopic.registerObserver(myObserver);
//        MyObserver observer1 = new MyObserver("wanghao",myTopic);
        MyObserver observer2 = new MyObserver("zhangdandan",myTopic);
        myTopic.push("hello");
    }
}

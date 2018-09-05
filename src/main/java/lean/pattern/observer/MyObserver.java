package lean.pattern.observer;

/**
 * Created by IntelliJ IDEA.
 * User: wanghao@weipass.cn
 * Date: 2016/7/19
 * Time: 13:25
 */
public class MyObserver implements Observer, DisplayMessage{
    
    private String name;
    
    private String message;
    
    private Subject topic;

    public MyObserver(){}
    
    public MyObserver(String name){
        this.name = name;
    }
    
    public MyObserver(String name,Subject topic){
        this.name = name;
        this.topic = topic;
        this.topic.registerObserver(this);
    }
    
    @Override
    public void update(String message) {
        this.message = message;
        show();
    }

    @Override
    public void show() {
        System.out.println(this.message + "----" + this.name);
    }
}

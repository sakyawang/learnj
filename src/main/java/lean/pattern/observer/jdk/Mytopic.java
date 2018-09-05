package lean.pattern.observer.jdk;

import java.util.Observable;

/**
 * Created by IntelliJ IDEA.
 * User: wanghao@weipass.cn
 * Date: 2016/7/19
 * Time: 13:46
 */
public class Mytopic extends Observable {
    
    public void push(String message){
        setChanged();
        notifyObservers(message);
    }
}

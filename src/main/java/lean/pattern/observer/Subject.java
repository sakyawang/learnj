package lean.pattern.observer;

/**
 * Created by IntelliJ IDEA.
 * User: wanghao@weipass.cn
 * Date: 2016/7/19
 * Time: 13:13
 */
public interface Subject {
    
    void registerObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObserver();
}

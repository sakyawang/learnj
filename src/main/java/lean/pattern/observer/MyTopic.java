package lean.pattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: wanghao@weipass.cn
 * Date: 2016/7/19
 * Time: 13:16
 */
public class MyTopic implements Subject {
    
    private List<Observer> observerList;
    
    private String message;
    
    public MyTopic(){
        this.observerList = new ArrayList<Observer>();
    }
    
    @Override
    public void registerObserver(Observer observer) {
        if(null != observer){
            observerList.add(observer);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        if(observer != null && observerList.indexOf(observer) >= 0){
            observerList.remove(observer);
        }
    }

    @Override
    public void notifyObserver() {
        for(Observer observer : observerList){
            observer.update(message);
        }
    }

    public void push(String message){
        this.message = message;
        notifyObserver();    
    }
    
}

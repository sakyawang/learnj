package lean.data.structure.stack.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/7/9.
 */
public class Stack<T> {

    private T t;

    private List<T> stack = new ArrayList<T>();

    public T pop(){
        t = stack.get(stack.size()-1);
        stack.remove(stack.size()-1);
        return t;
    }

    public void push(T t){

        stack.add(t);
    }

}

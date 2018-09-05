package lean.data.structure.stack.util;

import lean.data.structure.stack.bean.Stack;

/**
 * Created by Administrator on 2015/7/9.
 */
public class StackUtil {

    public static void main(String[] args) {

        Stack<String> stack = new Stack<String>();
        stack.push("wang");
        stack.push("hao");
        String pop = stack.pop();
        System.out.println(pop);
        pop = stack.pop();
        System.out.println(pop);

        Stack<Integer> integerStack = new Stack<Integer>();
        integerStack.push(222);
        integerStack.push(3333);
        Integer pops = integerStack.pop();
        System.out.println(pops);
        pops = integerStack.pop();
        System.out.println(pops);
    }
}

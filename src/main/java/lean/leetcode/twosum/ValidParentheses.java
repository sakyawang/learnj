package lean.leetcode.twosum;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

public class ValidParentheses {

    /**
     *
     * {} () [] {([])}
     *
     * @param s
     * @return
     */
    public boolean isValid(String s) {
        if(Objects.isNull(s) || s.isEmpty()) {
            return true;
        }
        Map<Character, Integer> map = new HashMap<>();
        map.put('{', 0);
        map.put('}', 1);
        map.put('[', 2);
        map.put(']', 3);
        map.put('(', 4);
        map.put(')', 5);
        int length = s.length();
        if (length % 2 != 0) {
            return false;
        }
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < length; i++) {
            char current = s.charAt(i);
            if(stack.isEmpty()) {
                stack.push(current);
            } else {
                Character peek = stack.peek();
                if(map.get(current) - map.get(peek) == 1) {
                    stack.pop();
                } else {
                    stack.push(current);
                }
            }
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        ValidParentheses parentheses = new ValidParentheses();
        System.out.println(parentheses.isValid("[({(())}[()])]"));
    }
}

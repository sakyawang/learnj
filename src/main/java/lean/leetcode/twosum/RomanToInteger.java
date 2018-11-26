package lean.leetcode.twosum;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RomanToInteger {

    public int romanToInt(String s) {
        if (Objects.isNull(s) || s.isEmpty()) {
            return 0;
        }
        Map<Character, Integer[]> map = new HashMap<>();
        map.put('I', new Integer[]{1, 1});
        map.put('V', new Integer[]{2, 5});
        map.put('X', new Integer[]{3, 10});
        map.put('L', new Integer[]{4, 50});
        map.put('C', new Integer[]{5, 100});
        map.put('D', new Integer[]{6, 500});
        map.put('M', new Integer[]{7, 1000});
        int result = 0;
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; ) {
            char firstChar = chars[i];
            Integer[] first = map.get(firstChar);
            int firstIndex = first[0];
            int firstValue = first[1];
            if(i != chars.length - 1) {
                char secondChar = chars[i+1];
                Integer[] second = map.get(secondChar);
                int secondIndex = second[0];
                int secondValue = second[1];
                if (secondIndex > firstIndex) {
                    result += secondValue - firstValue;
                    i+=2;
                } else {
                    result += firstValue;
                    i++;
                }
            } else {
                result += firstValue;
                i++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        RomanToInteger romanToInteger = new RomanToInteger();
        System.out.println(romanToInteger.romanToInt("MCMXCIV"));
    }
}

package lean.leetcode.twosum;

import java.math.BigInteger;

public class ReverseInteger {

    public int reverse(int x) {
        String s = String.valueOf(x);
        String prefix = "";
        if (s.startsWith("-")) {
            prefix = "-";
            s = s.substring(1, s.length());
        }
        char[] chars = s.toCharArray();
        char[] newchars = new char[chars.length];
        for (int i = chars.length -1, j = 0; i >= 0; i--, j++) {
            newchars[j] = chars[i];
        }
        String s1 = new String(newchars);
        StringBuilder sb = new StringBuilder();
        sb.append(prefix).append(s1);
        Long aLong = Long.valueOf(sb.toString());
        if(aLong > Integer.MAX_VALUE || aLong < Integer.MIN_VALUE) {
            return 0;
        }
        return Integer.valueOf(sb.toString());
    }

    public static void main(String[] args) {
        ReverseInteger reverseInteger = new ReverseInteger();
        System.out.println(reverseInteger.reverse(1534236469));
    }
}

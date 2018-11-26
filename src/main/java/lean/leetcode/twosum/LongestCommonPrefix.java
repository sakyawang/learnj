package lean.leetcode.twosum;

import java.util.Objects;

public class LongestCommonPrefix {

    public String longestCommonPrefix(String[] strs) {
        if (Objects.isNull(strs) || strs.length == 0) {
            return "";
        }
        String template = strs[0];
        char[] chars = template.toCharArray();
        StringBuffer buffer = new StringBuffer();
        boolean flag = false;
        char index;
        for (int j = 0; j < chars.length; j++) {
            if(flag) {
                break;
            }
            index = chars[j];
            for (int i = 0; i < strs.length; i++) {
                String str = strs[i];
                if (j > str.length()-1 || str.charAt(j) != index) {
                    flag = true;
                    break;
                }
            }
            if(!flag) {
                buffer.append(index);
            }
        }
        return buffer.toString();
    }

    public static void main(String[] args) {
        LongestCommonPrefix commonPrefix = new LongestCommonPrefix();
        String[] strs = {"aa","a"};
        System.out.println(commonPrefix.longestCommonPrefix(strs));
    }
}

package lean.leetcode.twosum;

public class PalindromeNumber {

    public boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        String s = String.valueOf(x);
        int length = s.length();
        int offset;
        String first;
        String second;
        if (length % 2 == 0) {
            offset = length / 2;
            first = s.substring(0, offset);
            second = new StringBuffer().append(s.substring(offset)).reverse().toString();
        } else {
            offset = (length + 1) / 2;
            first = s.substring(0, offset - 1);
            second = new StringBuffer().append(s.substring(offset)).reverse().toString();
        }
        return first.equals(second);
    }

    public static void main(String[] args) {
        int x = 12321;
        PalindromeNumber palindromeNumber = new PalindromeNumber();
        System.out.println(palindromeNumber.isPalindrome(x));
    }
}

package lean.data.structure.string;

/**
 * Created by IntelliJ IDEA.
 * User: wanghao@weipass.cn
 * Date: 2016/2/24
 * Time: 10:45
 */
public class ConvertString {

    private String originStr;

    public ConvertString(String originStr){
        this.originStr = originStr;
    }

    public String convertStr(){

        if(null == originStr || "".equals(originStr.trim())){
            return "";
        }
        int n = originStr.length();
        char[] chars = new char[n];
        originStr.getChars(0,originStr.length(),chars,0);
        reverseString(chars,0,n-1);
        return new String(chars);
    }

    public String convertStr(int m){

        if(null == originStr || "".equals(originStr.trim())){
            return "";
        }
        int n = originStr.length();
        if(m<=0){
            return originStr;
        }
        if(m > n){
            return convertStr();
        }
        m = n-m;
        char[] chars = new char[n];
        originStr.getChars(0,originStr.length(),chars,0);
        reverseString(chars,0,m-1);
        reverseString(chars,m,n-1);
        reverseString(chars,0,n-1);
        return new String(chars);
    }

    public String convertStr1(){

        if(null == originStr || "".equals(originStr.trim())){
            return "";
        }
        String[] strs = originStr.split(" ");
        reverseString(strs,0,strs.length-1);
        return strs.toString();
    }

    private void reverseString(String[] strs,int from,int to){
        while (from < to){
            String ch = strs[from];
            strs[from++] = strs[to];
            strs[to--] = ch;
        }
    }

    private void reverseString(char[] chars,int from,int to){
        while (from < to){
            char ch = chars[from];
            chars[from++] = chars[to];
            chars[to--] = ch;
        }
    }

    public String getOriginStr() {
        return originStr;
    }

    public void setOriginStr(String originStr) {
        this.originStr = originStr;
    }
}

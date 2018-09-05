package lean.regular;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 浩 on 2017/1/9.
 */
public class Main {


    public static void main(String[] args) {

        String addr = "321.12.12.12/24";
        String patternStr = "^(?!^255(\\.255){3}$)(?!^0{1,3}(\\.0{1,3}){3}$)((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)($|(?!\\.$)\\.)){4}$";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher patternMat = pattern.matcher(addr);
        System.out.println(patternMat.matches());
    }
}

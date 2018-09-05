package lean.collection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: wanghao@weipass.cn
 * Date: 2016/5/17
 * Time: 16:17
 */
public class ArrayTest {

    public static void main(String[] args) {

        List<String> strList = new ArrayList<String>();
        strList.add("wang");
        strList.add("hao");
        String[] strings = strList.toArray(new String[strList.size()]);
        for (String str : strings){
            System.out.println(str);
        }
    }
}

package lean.jdk8.lambda;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: wanghao@weipass.cn
 * Date: 2016/7/7
 * Time: 14:19
 */
public class TestLambda {


    public static void sort(List<String> arrays) {
        Collections.sort(arrays, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return 0;
            }
        });
    }

    public static void sortNew(List<String> arrays) {
        Collections.sort(arrays,(a,b)-> a.compareTo(b));
    }
    
    public static void show(List<String> arrays){
        if(null == arrays || arrays.isEmpty()){
            return;
        }
        for(String str : arrays){
            System.out.println(str);
        }
    }
    
    public static void main(String[] args) {

/*
        List<String> names = Arrays.asList("wanghao", "wangjie", "tianjunwei", "shiyan", "sunkai", "shaozhijun");
        TestLambda.sortNew(names);
        TestLambda.show(names);*/

        char a = '王';
        System.out.println(Character.reverseBytes(a));
        byte b = (byte)(a & 0xFF);
        System.out.println(a);
        System.out.println(b);
    }

}

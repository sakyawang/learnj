package lean.data.structure;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description TODO
 * @Author wanghao@cncloudsec.com
 * @Date 19-6-14 下午2:06
 */
public class HashMapTest {

    public static void main(String[] args) {
        Map<String, String> names = new HashMap<>();
        names.put("wang", "hao");
        names.put("wang1", "hao1");
        names.put("wang2", "hao2");
        names.put("wang3", "hao3");
        names.put("wang4", "hao4");
        names.put("wang5", "hao5");
        names.put("wang6", "hao6");
        names.put("wang7", "hao7");
        names.put("wang8", "hao8");
        names.put("wang9", "hao9");
        names.put("wang10", "hao10");
        names.put("wang11", "hao11");
        System.out.println(names);
        names.put("wang12", "hao12");
        names.keySet().stream().forEach(key -> {
            System.out.println(key);
            System.out.println(names.get(key));
        });
    }
}

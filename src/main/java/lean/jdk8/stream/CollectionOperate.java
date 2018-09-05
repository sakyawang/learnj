package lean.jdk8.stream;

import lean.rpc.user.po.User;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Created by 浩 on 2016/9/29.
 */
public class CollectionOperate {

    public static void main(String[] args) {

        List<String> a = Arrays.asList("a", "b", "c");
        List<String> b = Arrays.asList("a", "b", "c", "d");
        System.out.println(b.containsAll(a));

        Map<String, User> synchronizedMap = Collections.synchronizedMap(new HashMap<>());
        User user1 = new User();
        user1.setName("wanghao");
        user1.setAge(28);
        synchronizedMap.put(user1.getName(), user1);
        synchronizedMap.values().forEach((user)-> System.out.println(user.getAge()));
        synchronizedMap.keySet().stream().forEach((key)-> synchronizedMap.get(key).setAge(29));
        synchronizedMap.values().forEach((user)-> System.out.println(user.getAge()));
        List<String> list = new ArrayList<String>() {
            {
                add("wang");
                add("dandan");
                add("hao");
                add("hao");
            }
        };
        Stream<String> stream = list.stream();
        final String prefix = "test";
        stream.distinct().map((x) -> {
            return prefix + x;
        }).forEach(System.out::println);
    }

}

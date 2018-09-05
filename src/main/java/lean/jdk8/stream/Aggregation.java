package lean.jdk8.stream;

import lean.jdk8.stream.domain.SecurityServicePacket;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Aggregation {

    interface IntegerMath {
        int operation(int a, int b);
    }

    public int operateBinary(int a, int b, IntegerMath op) {
        return op.operation(a, b);
    }

    public static void main(String[] args) {
        int type1 = 1;
        int type2 = 2;
        String policyStateSuccess = "success";
        String policyStateFail = "fail";
        List<SecurityServicePacket> packets = Arrays.asList(new SecurityServicePacket(1, type1, policyStateSuccess),
                new SecurityServicePacket(2, type1, policyStateSuccess),
                new SecurityServicePacket(3, type2, policyStateFail),
                new SecurityServicePacket(4, type2, policyStateSuccess));
        long count = packets.stream().count();

        Map<Integer, Long> collect = packets.stream().collect(Collectors.groupingBy(SecurityServicePacket::getType, Collectors.counting()));
        Map<String, Long> collect1 = packets.stream().collect(Collectors.groupingBy(SecurityServicePacket::getPolicyState, Collectors.counting()));

        collect.keySet().forEach((item)->{
            System.out.println(item + ":" + collect.get(item));
        });

        collect1.keySet().forEach((item)->{
            System.out.println(item + ":" + collect1.get(item));
        });
        System.out.println(count);

        IntegerMath addtion = (a, b) -> a + b;
    }
}

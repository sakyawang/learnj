package lean.jdk8.stream;

import com.google.common.collect.Lists;
import lean.jdk8.stream.domain.SecurityServicePacket;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.Instant.ofEpochMilli;
import static java.time.LocalDateTime.ofInstant;

/**
 * @Description TODO
 * @Author wanghao@cncloudsec.com
 * @Date 19-5-15 上午10:08
 */
public class Main {

    public static void main(String[] args) {
        List<SecurityServicePacket> servicePackets = Lists.newArrayList(
                new SecurityServicePacket(8, 1, ""),
                new SecurityServicePacket(7, 1, ""),
                new SecurityServicePacket(9, 1, ""));
        List<SecurityServicePacket> sorted = servicePackets.stream()
                .sorted(Comparator.comparing(SecurityServicePacket::getId))
                .collect(Collectors.toList());
        servicePackets.stream().forEach(System.out::println);
        sorted.stream().forEach(System.out::println);
        List<Long> collect = sorted.stream().map(item -> item.getId()).collect(Collectors.toList());

        int time = -8 * 3600 * 1000;
        String[] iDs = TimeZone.getAvailableIDs(time);
        Date date = new Date(1557861000000l);
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(date.getTime());
        System.out.println(instance.getTime().toString());
        instance.add(Calendar.HOUR, -8);
        System.out.println(instance.getTime());
        System.out.println(iDs);
        LocalDateTime localDateTime = ofInstant(ofEpochMilli(1557861000000l), TimeZone.getTimeZone(iDs[0]).toZoneId());
        System.out.println(localDateTime.toString());
        System.out.println(localDateTime.toLocalDate().toString()+" "+localDateTime.getHour()+":"+localDateTime.getMinute()+":00");
    }
}

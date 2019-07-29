package lean.base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description TODO
 * @Author wanghao@cncloudsec.com
 * @Date 19-4-30 上午9:24
 */
public class RegexTest {

    private static final Pattern patternBase = Pattern.compile("([\\S\\s]+);([0-9]{1}[\\s]+);([\\s]+[\\S]+[\\s]+);([\\S\\s]+);([\\S\\s]+)");

    private static final Pattern pattern1 = Pattern.compile("([\\S\\s]+[0-9]{1}[\\s]+);([\\S\\s]+);([\\S\\s]+);([\\S\\s]+);([\\S\\s]+);([\\S\\s]+);([\\S\\s]+);([\\S\\s]+);([\\S\\s]+)");

    private static final Pattern pattern2 = Pattern.compile("([\\S\\s]+[0-9]{1}[\\s]+);([\\S\\s]+);([\\S\\s]+);([\\S\\s]+);([\\d\\s]+);([\\d\\s]+);([\\d\\s]+);([\\d\\s]+);([\\d\\s]+)");

    private static final Pattern fwPatternBase = Pattern.compile("([\\S\\s]+);\\s([\\S\\s]+):\\s([\\S\\s]+)");

    private static final Pattern fwPatternHealth = Pattern.compile("([\\S\\s]+);\\s([\\S\\s]+):\\s[\\s\\S]+=([\\S\\s]+);[\\s\\S]+=([\\s\\S]+);[\\s\\S]+=([\\s\\S]+);[\\s\\S]+=([\\s\\S]+);[\\s\\S]+=([\\S]+)");

    private static final Pattern fwPatternIps = Pattern.compile("([\\S\\s]+);\\sips:\\suser_id=([\\S\\s]+);user_name=([\\s\\S]+);policy_id=([\\s\\S]+);src_mac=([\\s\\S]+);dst_mac=([\\s\\S]+);src_ip=([\\s\\S]+);dst_ip=([\\s\\S]+);src_port=([\\s\\S]+);dst_port=([\\s\\S]+);app_name=([\\s\\S]+);protocol=([\\s\\S]+);app_protocol=([\\s\\S]+);event_id=([\\s\\S]+);event_name=([\\s\\S]+);event_type=([\\s\\S]+);level=([\\s\\S]+);ctime=([\\s\\S]+);action=([\\S]+)");

    private static final Pattern fwPatternWebAccess = Pattern.compile("([\\S\\s]+);\\s([\\S\\s]+):\\suser_name=([\\S\\s]+);user_group_name=([\\s\\S]+);term_platform=([\\s\\S]*);term_device=([\\s\\S]*);src_ip=([\\s\\S]+);dst_ip=([\\s\\S]+);url_domain=([\\s\\S]+);url=([\\s\\S]+);url_cate_name=([\\s\\S]+);handle_action=([\\s\\S]+);msg=([\\s\\S]*)");

    private static final Pattern fwPatternStatisticTraffic = Pattern.compile("([\\S\\s]+);\\s([\\S\\s]+):\\suser_name=([\\S\\s]+);ugname=([\\s\\S]+);umac=([\\s\\S]*);uip=([\\s\\S]*);appname=([\\s\\S]+);appgname=([\\s\\S]+);up=([\\s\\S]+);down=([\\s\\S]+);create_time=([\\s\\S]+);end_time=([\\s\\S]+)");

     private static final Pattern wafRuledefed = Pattern.compile("([\\S\\s]+);\\s([\\S\\s]+):\\spolicy_name=([\\S\\s]+);url=([\\s\\S]+);waf_method=([\\s\\S]*);src_mac=([\\s\\S]*);dst_mac=([\\s\\S]+);src_ip=([\\s\\S]+);dst_ip=([\\s\\S]+);src_port=([\\s\\S]+);dst_port=([\\s\\S]+);rule_id=([\\s\\S]+);defend_type=\"([\\s\\S]+)\";level=([\\s\\S]+);action=([\\s\\S]+);msg=\"([\\s\\S]+)\"");

    public static void main(String[] args) throws ParseException {
        String content1 = "ABT ;F109001000004932B7205C9E ;ipv4 ;4 ; user_statistic_traffic ;192.168.2.100 ;192.168.2.100 ;38456 ;41264 ;79720 ;1556537040 ;1556537100 ";
        String content2 = "ABT ;F109001000004932B7205C9E ;ipv4 ;4 ; apt ;199.168.1.51 ;10.1.1.12 ;123 ;80 ;0 ;1 ;1 ";
        String app = "ABT ;F109001000004932B7205C9E ;ipv4 ;4 ; app_statistic_traffic ;域名解析协议(DNS) ;928 ;0 ;928 ;1557026460 ;1557026520 ";
        String content3 = "ABT\u001E;F109001000004932B7205C9E\u001E;ipv4\u001E;4\u001E; app_statistic_traffic\u001E;安全套接层协议(SSL)\u001E;171680\u001E;1722272\u001E;1893952\u001E;1557038820\u001E;1557038880 ";

        String content4 = "ABT;190014000609108425758737;ipv4;3; statistic_traffic: user_name=10.2.6.6;ugname=anonymous;umac=00:50:56:B2:05:91;uip=10.2.6.6;appname=TCP;appgname=网络协议;up=196136;down=41064;create_time=1561689360;end_time=1561689420";

        String content5 = "NGFW;190014000650393037525683;ipv4;3; waf_ruledefend: policy_name=web;url=\"http://192.168.103.2/vgn/previewer\";waf_method=GET;src_mac=00:1a:4a:16:01:5e;dst_mac=00:1a:4a:16:01:70;src_ip=192.168.104.2;dst_ip=192.168.103.2;src_port=59706;dst_port=80;rule_id=906001;defend_type=\"恶意扫描与爬虫\";level=crit;action=拒绝;msg=\"通用扫描程序, User-Agent(mozilla/4.75 (nikto/2.01 ))中检测到\"\"nikto\"\"\"\n";

        SimpleDateFormat sss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sss.parse("2019-05-06 10:25:58");
        System.out.println(date.getTime());
        /*Matcher matcher = pattern2.matcher(app);
        printMatchGroup(matcher);
        matcher = pattern1.matcher(content1);
        printMatchGroup(matcher);
        matcher = pattern2.matcher(content2);
        printMatchGroup(matcher);
        matcher = patternBase.matcher(content1);
        printMatchGroup(matcher);
        matcher = patternBase.matcher(content3);
        printMatchGroup(matcher);
        String trim = content3.trim();
        System.out.println(trim);*/
        String fw = "HOST;000014000699906285795121;ipv4;3; device_health: cpu_used=0;mem_used=20;disk_used=0;temperature=0;session_num=204";
        String fwIps = "HOST;000014000699906285795121;ipv4;3; ips: user_id=2;user_name=10.2.6.30;policy_id=1;src_mac=00:50:56:b2:51:6e;dst_mac=00:50:56:b2:66:5e;src_ip=10.2.6.30;dst_ip=10.2.6.1;src_port=65161;dst_port=8360;app_name=网页浏览(HTTP);protocol=TCP;app_protocol=HTTP;event_id=1311495;event_name=HTTP_Nikto_WEB漏洞扫描;event_type=安全扫描;level=warning;ctime=2019-05-06 10:25:58;action=pass";
        String webAccess = "HOST;000014000699906285795121;ipv4;3; web_access: user_name=10.2.6.30;user_group_name=anonymous;term_platform=;term_device=;src_ip=10.2.6.30;dst_ip=219.232.200.2;url_domain=www.bjrbj.gov.cn;url=http://www.bjrbj.gov.cn/;url_cate_name=www.bjrbj.gov.cn;handle_action=0;msg=";
        String url = "HOST;000014000699906285795121;ipv4;3; web_access: user_name=10.2.6.30;user_group_name=anonymous;term_platform=;term_device=;src_ip=10.2.6.30;dst_ip=111.161.111.57;url_domain=q.i.gdt.qq.com;url=http://q.i.gdt.qq.com/gdt_view.fcg?adposcount=1&uin=419739377&stkey=FBDCE4981DA26F6677558F013F758360F06D48C866DB77834D07A43B4D9EAFF7AB5CFC5120ED7E13F1C0C7B8D59C60D59FE8D7ABF5D9FB2705BE3E5EF0BF675B7CB240FEDB37BECB787897C64A1C4CB5B2A43CD6503B43D0&encid=2&datatype=2&posid=5090226844959332|&sext=soHhDZirTt64NftG26fIrNi%2FLJncntESfnumJIo1WAtqkTrusQVUYld6zj5pd2yTke82cCoigKxTKtSZYhHrqdOulkUXVEjvYajtX%2FzPOjuLahU2bh2NvdBhXT1at6qw%2ByKmDvdoa29MWS%2FY11qcaHT5zpUSUq97sUgm7SzVszLG6ZffibCb9Dn0MFFupI0SO24uXW6YdFevSDAdXJhltkhssdV8coZZn9SGUTJJoAYof92eqMhgc7lc1wUsUUTm&clientinfo=QQ2013&reqcount=4&delayedtime=0&freshinfo=null&brand=1&last_ads=%7B%22responsed_ad_data%22%3A%22ybQVka9HbvyOihGK5XlOCr8GsR_gvr1t3sfgPjK_dSKdLgy58qusV7i3zOjxJ04vk%21eVVEgg8tk%22%7D;url_cate_name=广告;handle_action=1;msg=";
        Matcher matcher = fwPatternBase.matcher(fw);
//        printMatchGroup(matcher);
        matcher = fwPatternHealth.matcher(fw);
//        printMatchGroup(matcher);
        matcher = fwPatternIps.matcher(fwIps);
        printMatchGroup(matcher);
        matcher = fwPatternWebAccess.matcher(webAccess);
        printMatchGroup(matcher);
        matcher = fwPatternWebAccess.matcher(url);
        printMatchGroup(matcher);
        matcher = fwPatternStatisticTraffic.matcher(content4);
        printMatchGroup(matcher);
        matcher = wafRuledefed.matcher(content5);
        printMatchGroup(matcher);
    }

    private static void printMatchGroup(Matcher matcher) {
        if (matcher.find()) {
            for(int i = 1; i <= matcher.groupCount(); i++) {
                System.out.println(matcher.group(i));
            }
        } else {
            System.out.println("ddd");
        }
    }
}

package lean.base;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Strings {

    public static void main(String[] args) {
        String a = "sss";
        String b = "sss";
        System.out.println(a == b);
        System.out.println(a.equals(b));
        String c = new String("sss");
        String d = new String("sss");
        System.out.println(c == d);
        System.out.println(c.equals(d));
        String e = "sss";
        String f = e;
        System.out.println( e == f);
        System.out.println(e.equals(f));
        String g = new String("sss");
        String h = "sss";
        System.out.println(g == h);
        System.out.println(g.equals(h));
        System.out.println(h == a);

        long time = 1557480960000l;

        Date date = new Date(time);
        System.out.println(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        String format = dateFormat.format(date);
        System.out.println(format);
    }
}

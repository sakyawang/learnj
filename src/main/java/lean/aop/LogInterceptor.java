package lean.aop;

import org.nutz.aop.InterceptorChain;
import org.nutz.aop.MethodInterceptor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.out;
import static java.time.Instant.ofEpochMilli;
import static java.time.LocalDateTime.ofInstant;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.TimeZone.getDefault;

public class LogInterceptor implements MethodInterceptor {

    @Override
    public void filter(InterceptorChain interceptorChain) throws Throwable {
        DateTimeFormatter formatter = ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = ofInstant(ofEpochMilli(currentTimeMillis()), getDefault().toZoneId());
        out.println("run start at:" + dateTime.format(formatter));
        interceptorChain.doChain();
        dateTime = ofInstant(ofEpochMilli(currentTimeMillis()), getDefault().toZoneId());
        out.println("run after at:" + dateTime.format(formatter));
    }
}

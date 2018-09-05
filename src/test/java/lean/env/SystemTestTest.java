package lean.env;

import org.junit.Test;

import java.lang.*;

/**
 * Created by wanghao@weipass.cn on 2015/7/22.
 */
public class SystemTestTest {

    @Test
    public void testSetSystemProperties1() throws Exception {
        String s = SystemTest.setSystemProperties("env", "idc");
        System.out.print(s);

    }

    @Test
    public void testGetSystemProperties1() throws Exception {
        String env = SystemTest.getSystemProperties("env");
        System.out.print(env);
    }

}

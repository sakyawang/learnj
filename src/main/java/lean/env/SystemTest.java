package lean.env;

/**
 * Created by wanghao@weipass.cn on 2015/7/22.
 */
public class SystemTest {

    public boolean flag;

    public static String setSystemProperties(String key, String value) {
        String s = java.lang.System.setProperty(key, value);
        return s;
    }

    public static String getSystemProperties(String key) {

        return java.lang.System.getProperty(key);
    }



    public static void main(String[] args) {

       /* String value = "umask 0077; " +
                "MYTMP=\"$(TMPDIR=\"${OVIRT_TMPDIR}\" mktemp -d -t ovirt-XXXXXXXXXX)\"; " +
                "trap \"chmod -R u+rwX \\\"${MYTMP}\\\" > /dev/null 2>&1; rm -fr \\\"${MYTMP}\\\" > /dev/null 2>&1\" 0; " +
                "tar --warning=no-timestamp -C \"${MYTMP}\" -x && " +
                "@ENVIRONMENT@ \"${MYTMP}\"/@ENTRY@ DIALOG/dialect=str:machine DIALOG/customization=bool:True";
        String result = value.replace("@ENTRY@","ovirt-host-deploy").replace("@ENVIRONMENT@","");
        System.out.println(result);*/
        SystemTest test = new SystemTest();
        System.out.println(test.flag);
    }

}

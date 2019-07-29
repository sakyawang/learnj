package lean.ipdb;

import net.ipip.ipdb.City;
import net.ipip.ipdb.CityInfo;
import net.ipip.ipdb.IPFormatException;

import java.io.IOException;

/**
 * @Description TODO
 * @Author wanghao@cncloudsec.com
 * @Date 19-6-29 下午4:26
 */
public class Main {

    private static final String IP_DB_FILE = "ip/ipipfree.ipdb";

    public static void main(String[] args) throws IOException, IPFormatException {
        String path = Main.class.getClassLoader().getResource(IP_DB_FILE).getPath();
        City db = new City(path);
        CityInfo cityInfo = db.findInfo("114.242.29.156", "CN");
        System.out.println(cityInfo);
    }
}

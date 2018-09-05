package lean.data.structure.string;

import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: wanghao@weipass.cn
 * Date: 2016/2/24
 * Time: 10:47
 */
public class ConvertStringTest {

    @Test
    public void testConvertStr() throws Exception {
        ConvertString convert = new ConvertString("I am a student.");
        String convertStr = convert.convertStr();
        System.out.println(convertStr);
    }
}
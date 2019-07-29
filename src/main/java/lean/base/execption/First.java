package lean.base.execption;

/**
 * @Description TODO
 * @Author wanghao@cncloudsec.com
 * @Date 19-5-22 上午9:52
 */
public class First {

    public void foo() {
        Second second = new Second();
        second.foo();
    }
}

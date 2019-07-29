package lean.reactivex;

import io.reactivex.Flowable;

/**
 * @Description TODO
 * @Author wanghao@cncloudsec.com
 * @Date 19-2-26 上午10:46
 */
public class First {

    public static void hello(String... args) {
        Flowable.fromArray(args).subscribe(s -> System.out.println("Hello " + s + "!"));
    }

    public static void main(String[] args) {
        First.hello("sakyawang", "zhdd");
    }
}

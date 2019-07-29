package lean.base;

import com.google.common.base.MoreObjects;

/**
 * Created by 浩 on 2016/11/21.
 */
public class Scope {

    private String privateStr = "privateStr";

    public String publicStr = "publicStr";

    String defaultStr = "defaultStr";

    protected String protectedStr = "protectedStr";

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("privateStr", privateStr)
                .add("publicStr", publicStr)
                .add("defaultStr", defaultStr)
                .add("protectedStr", protectedStr)
                .toString();
    }
}

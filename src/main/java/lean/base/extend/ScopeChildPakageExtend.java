package lean.base.extend;

import lean.base.Scope;

/**
 * Created by 浩 on 2016/11/21.
 */
public class ScopeChildPakageExtend extends Scope{

    public static void main(String[] args) {

        Scope scope = new Scope();
        System.out.println(scope.publicStr);
        ScopeChildPakageExtend extend = new ScopeChildPakageExtend();
        System.out.println(extend.protectedStr);
        System.out.println(extend.publicStr);
    }
}

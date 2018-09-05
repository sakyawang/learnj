package lean.basetest;

import lean.base.Scope;

/**
 * Created by 浩 on 2016/11/21.
 */
public class ScopeOutPacageExtendTest extends Scope{


    public static void main(String[] args) {
        Scope scope = new Scope();
        System.out.println(scope.publicStr);
        ScopeOutPacageExtendTest extend = new ScopeOutPacageExtendTest();
        System.out.println(extend.publicStr);
        System.out.println(extend.publicStr);
    }
}

package lean.basetest;

import lean.base.Scope;

/**
 * Created by 浩 on 2016/11/21.
 */
public class ScopeOutPackageTest {

    public static void main(String[] args) {
        Scope scope = new Scope();
        System.out.println(scope.publicStr);
    }
}

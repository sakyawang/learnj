package lean.base;

/**
 * Created by 浩 on 2016/11/21.
 */
public class ScopeExtend extends Scope{

    public static void main(String[] args) {

        Scope scope = new Scope();
        System.out.println(scope.publicStr);
        System.out.println(scope.defaultStr);
        System.out.println(scope.protectedStr);
        ScopeExtend scopeExtend = new ScopeExtend();
        System.out.println(scopeExtend.publicStr);
        System.out.println(scopeExtend.protectedStr);
        System.out.println(scopeExtend.defaultStr);
    }
}

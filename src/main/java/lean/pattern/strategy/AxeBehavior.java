package lean.pattern.strategy;

/**
 * Created by IntelliJ IDEA.
 * User: wanghao@weipass.cn
 * Date: 2016/7/18
 * Time: 9:01
 */
public class AxeBehavior implements WeaponBehavior {
    
    @Override
    public void useWeapon() {
        System.out.println("weapon with axe");
    }
}

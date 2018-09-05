package lean.pattern.strategy;

/**
 * Created by IntelliJ IDEA.
 * User: wanghao@weipass.cn
 * Date: 2016/7/18
 * Time: 9:15
 */
public class King extends Character {

    public King() {
        weaponBehavior = new BowAndArrowBehavior();
    }

    @Override
    public void fight() {  
        weaponBehavior.useWeapon();
    }
}

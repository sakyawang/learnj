package lean.pattern.strategy;

/**
 * Created by IntelliJ IDEA.
 * User: wanghao@weipass.cn
 * Date: 2016/7/18
 * Time: 9:09
 */
public class TestMain {

    public static void main(String[] args) {
        Character queen = new Queen();
        queen.fight();
        queen.setWeaponBehavior(new BowAndArrowBehavior());
        queen.fight();
    }
}

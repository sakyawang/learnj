package lean.jdk8.thread;

/**
 * Created by 浩 on 2016/9/29.
 */
public class Main {

    public static void main(String[] args) {

        boolean result = false;
        try {
            result = ApplicationStartupUtil.verify();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("External service verify conpleted!! result is:" + result);
    }
}

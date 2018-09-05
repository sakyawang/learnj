package lean.field;

import java.util.Arrays;

/**
 * Created by 浩 on 2017/7/4.
 */
public class Transient {
    int x,y;
    transient float m,n;


    public static void main(String[] args) {
        Transient aTransient = new Transient();
        aTransient.x = 10;
        aTransient.y = 20;
        aTransient.m = 2.2f;
        aTransient.n = 3.3f;

        Arrays.asList(aTransient.getClass().getDeclaredFields()).stream().forEach(field -> {
            try {
                System.out.println(field.get(aTransient));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }
}

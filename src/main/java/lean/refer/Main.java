package lean.refer;

import lean.serialize.User;

public class Main {

    public void update(User user, int age) {
        user.setAge(age);
    }

    public User update(User user) {
        user.setAge(30);
        return user;
    }

    public static void main(String[] args) {
        User user = new User();
        user.setName("王浩");
        user.setAge(29);
        System.out.println(user.hashCode());
        Main main = new Main();
        User update = main.update(user);
        System.out.println(update.hashCode());
    }

}

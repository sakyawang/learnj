package lean.annotation;

/**
 * Created by IntelliJ IDEA.
 * User: wanghao@weipass.cn
 * Date: 2016/7/18
 * Time: 10:34
 */
public class User {
    
    private int age;
    
    private String gender;
    
    private int id;
    
    private String name;
    
    public User(int age, String gender, int id, String name) {
        this.age = age;
        this.gender = gender;
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "age=" + age +
                ", gender='" + gender + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

package lean.base.clone;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends Person {

    @JSONField(serialzeFeatures={SerializerFeature.WriteMapNullValue})
    private String name;

    private int age;

    private List<Student> students = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public static void main(String[] args) {
        Teacher teacher = new Teacher();
        String s = JSON.toJSONString(teacher, SerializerFeature.WriteMapNullValue);
        System.out.println(s);
    }
}

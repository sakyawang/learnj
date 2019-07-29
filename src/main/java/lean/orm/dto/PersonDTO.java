package lean.orm.dto;

import com.google.common.base.MoreObjects;

public class PersonDTO {

    private String myName;

    private String address;

    private int age;

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("myName", myName)
                .add("address", address)
                .add("age", age)
                .toString();
    }
}

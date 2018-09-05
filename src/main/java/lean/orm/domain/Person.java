package lean.orm.domain;

import lean.orm.dto.DTO;
import lean.orm.dto.DtoField;
import lean.orm.dto.PersonDTO;

@DTO(type = PersonDTO.class)
public class Person {

    @DtoField(name = "myName", paramType = String.class)
    private String name;

    @DtoField(paramType = int.class)
    private int age;

    private String address;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        Person person = Person.class.newInstance();
    }
}

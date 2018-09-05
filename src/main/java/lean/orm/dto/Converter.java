package lean.orm.dto;

import lean.orm.domain.Person;
import org.nutz.lang.Strings;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Converter {

    public static Object toDto(Object origin) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        Field[] fields = origin.getClass().getDeclaredFields();
        DTO dto = origin.getClass().getAnnotation(DTO.class);
        Class<?> type = dto.type();
        Object dtoObj = type.newInstance();
        Arrays.asList(fields).forEach(field -> {
            DtoField dtoField = field.getAnnotation(DtoField.class);
            if (null != dtoField) {
                String fieldName = field.getName();
                String dtoFieldName = Strings.isEmpty(dtoField.name()) ? fieldName : dtoField.name();
                String setMethodName = "set" + Strings.upperFirst(dtoFieldName);
                String getMethodName = "get" + Strings.upperFirst(fieldName);
                try {
                    Method getMethod = origin.getClass().getDeclaredMethod(getMethodName);
                    Method setMethod = type.getDeclaredMethod(setMethodName, dtoField.paramType());
                    setMethod.invoke(dtoObj, getMethod.invoke(origin, new Class[0]));
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
        PersonDTO dtoObj1 = (PersonDTO) dtoObj;
        System.out.println(dtoObj1.getMyName());
        return null;
    }

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {

        Person person = new Person();
        person.setName("wanghao");
        person.setAge(30);
        person.setAddress("peking");
        try {
            Converter.toDto(person);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

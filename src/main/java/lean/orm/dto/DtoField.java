package lean.orm.dto;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface DtoField {

    String name() default "";

    Class<?> paramType();
}

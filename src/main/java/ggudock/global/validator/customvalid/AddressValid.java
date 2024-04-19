package ggudock.global.validator.customvalid;

import ggudock.global.validator.validator.AddressValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AddressValidator.class)
public @interface AddressValid {
    String message() default "주소를 다시 입력해주세요";

    Class[] groups() default {};

    Class[] payload() default {};

    int max() default 50;
    int min() default 0;
}

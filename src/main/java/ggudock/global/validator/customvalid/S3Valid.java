package ggudock.global.validator.customvalid;

import ggudock.global.validator.validator.S3Validator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = S3Validator.class)
public @interface S3Valid {
    String message() default "올바르지 않은 사진입니다";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

package ggudock.global.validator.customvalid;

import ggudock.global.validator.validator.NicknameValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NicknameValidator.class)
public @interface NicknameValid {
    String message() default "닉네임은 2~10글자 사이로 설정 가능합니다.";
    Class[] groups() default {};
    Class[] payload() default {};

    int min() default 2;
    int max() default 8;
}

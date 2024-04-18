package ggudock.validator.customvalid;

import ggudock.validator.validator.RatingValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RatingValidator.class)
public @interface RatingValid {
    String message() default "별점은 0~5점 사이입니다.";
    Class[] groups() default {};
    Class[] payload() default {};

    long min() default 0;
    long max() default 5;
}

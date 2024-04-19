package ggudock.global.validator.validator;

import ggudock.global.validator.customvalid.RatingValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RatingValidator implements ConstraintValidator<RatingValid,Long> {

    private long min;
    private long max;
    private String message;

    @Override
    public void initialize(RatingValid constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {

        if(value==null)
            return false;
        boolean isValid = value >= min && value <= max;

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
        }

        return isValid;
    }
}

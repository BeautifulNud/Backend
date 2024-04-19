package ggudock.global.validator.validator;

import ggudock.global.validator.customvalid.NicknameValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NicknameValidator implements ConstraintValidator<NicknameValid, String> {

    private int min;
    private int max;
    private String message;

    @Override
    public void initialize(NicknameValid constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String nickname, ConstraintValidatorContext context) {
        if (nickname == null)
            return false;
        boolean isValid = nickname.length() >= min && nickname.length() <= max;

        if(!isValid){
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
        }

        return isValid;
    }
}

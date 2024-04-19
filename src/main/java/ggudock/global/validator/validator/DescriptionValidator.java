package ggudock.global.validator.validator;

import ggudock.global.validator.customvalid.DescriptionValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DescriptionValidator implements ConstraintValidator<DescriptionValid,String> {

    private int size;
    private String message;

    @Override
    public void initialize(DescriptionValid constraintAnnotation) {
        this.size = constraintAnnotation.size();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String description, ConstraintValidatorContext context) {
        if(description==null)
            return false;
        boolean isValid = description.length()<=size;

        if(!isValid){
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
        }

        return isValid;
    }
}

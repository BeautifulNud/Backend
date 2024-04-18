package ggudock.validator.validator;


import ggudock.validator.customvalid.AddressValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AddressValidator implements ConstraintValidator<AddressValid,String> {

    private int min;
    private int max;
    private String message;

    @Override
    public void initialize(AddressValid constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String address, ConstraintValidatorContext context) {
        if(address==null)
            return false;
        boolean isValid = address.length() >= min && address.length()<=max;

        if(!isValid){
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
        }

        return isValid;
    }
}

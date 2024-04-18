package ggudock.validator.validator;

import ggudock.validator.customvalid.S3Valid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class S3Validator implements ConstraintValidator<S3Valid,String> {

    private static final String S3_REGEX =
            "\"^(https://)?([a-zA-Z0-9]+)\\.[a-z]+([a-zA-z0-9.?#]+)?";
    private Pattern pattern;
    private String message;

    @Override
    public void initialize(S3Valid constraintAnnotation) {
        pattern = Pattern.compile(S3_REGEX);
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String S3, ConstraintValidatorContext context) {
        if(S3 == null)
            return false;
        boolean isValid = pattern.matcher(S3).matches();

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
        }

        return isValid;
    }
}

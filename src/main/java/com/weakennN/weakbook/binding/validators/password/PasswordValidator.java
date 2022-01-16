package com.weakennN.weakbook.binding.validators.password;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordValidatorConstraint, String> {

    @Override
    public void initialize(PasswordValidatorConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;

        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                hasDigit = true;
            } else if (Character.isLowerCase(password.charAt(i))) {
                hasLowerCase = true;
            } else if (Character.isUpperCase(password.charAt(i))) {
                hasUpperCase = true;
            }
        }

        if (!hasDigit) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Password must contain at least one digit!")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            return false;
        } else if (!hasLowerCase) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Password must contain at least one lower case letter!")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            return false;
        } else if (!hasUpperCase) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Password must contain at least one upper case letter!")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            return false;
        }

        return true;
    }
}

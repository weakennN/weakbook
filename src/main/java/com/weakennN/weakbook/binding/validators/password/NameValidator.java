package com.weakennN.weakbook.binding.validators.password;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<NameValidatorConstraint, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s.length() <= 0)
            return false;
        boolean containsNumbers = false;
        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i))) {
                containsNumbers = true;
            }
        }

        return Character.isUpperCase(s.charAt(0)) && !containsNumbers;
    }
}

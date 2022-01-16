package com.weakennN.weakbook.binding.validators.password;

import com.weakennN.weakbook.binding.UserRegisterBinding;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ConfirmPasswordValidator implements ConstraintValidator<ConfirmPasswordValidatorConstraint, UserRegisterBinding> {

    @Override
    public boolean isValid(UserRegisterBinding userRegisterBinding, ConstraintValidatorContext constraintValidatorContext) {
        return userRegisterBinding.getPassword().equals(userRegisterBinding.getConfirmPassword());
    }
}

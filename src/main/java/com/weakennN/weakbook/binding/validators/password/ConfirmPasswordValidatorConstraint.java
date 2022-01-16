package com.weakennN.weakbook.binding.validators.password;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Constraint(validatedBy = ConfirmPasswordValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfirmPasswordValidatorConstraint {

    String message() default "Passwords don't match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package com.bestteam.urlshorter.validator;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConstraint {
    String message() default "Invalid password. The password must be at least 8 characters long, include digits, uppercase and lowercase letters.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

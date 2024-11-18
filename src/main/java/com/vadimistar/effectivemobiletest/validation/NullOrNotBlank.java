package com.vadimistar.effectivemobiletest.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target( {ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NullOrNotBlankValidator.class)
public @interface NullOrNotBlank {

    String message() default "не может быть пустым";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default {};
}

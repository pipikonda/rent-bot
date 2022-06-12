package com.pipikonda.rentbot.controller.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DefaultLangValidator.class)
public @interface DefaultLang {

    String message() default "The map should contain message on default language (ru)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

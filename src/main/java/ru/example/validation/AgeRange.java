package ru.example.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.example.validation.validator.AgeRangeValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AgeRangeValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AgeRange {

    String message() default "Недопустимый возраст";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int min() default 0;
    int max() default 150;
}
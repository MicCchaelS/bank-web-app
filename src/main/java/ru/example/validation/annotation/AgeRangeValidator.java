package ru.example.validation.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import ru.example.validation.annotation.AgeRange;

import java.time.LocalDate;
import java.time.Period;

@Component
public class AgeRangeValidator implements ConstraintValidator<AgeRange, LocalDate> {

    private int min;
    private int max;

    @Override
    public void initialize(AgeRange constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(LocalDate birthdate, ConstraintValidatorContext context) {
        if (birthdate == null) {
            return true;
        }

        int age = Period.between(birthdate, LocalDate.now()).getYears();

        return age >= min && age <= max;
    }
}
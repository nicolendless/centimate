package com.example.expense_tracker.validation;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.example.expense_tracker.entities.Category;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CategoryValidator implements ConstraintValidator<ValidCategory, String> {

    private String allowedValues;

    @Override
    public void initialize(ValidCategory coonstraintAnnotation) {
        allowedValues = Arrays.stream(Category.values())
            .map(Enum::name)
            .collect(Collectors.joining(", "));
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return false;
        }

        boolean isValid = Arrays.stream(Category.values())
            .anyMatch(category -> category.name().equalsIgnoreCase(value));

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                "Invalid category. Must be one of: " + allowedValues
            ).addConstraintViolation();
        }

        return isValid;
    }
    
}

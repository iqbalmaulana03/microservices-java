package com.iqbal.inventoryservice.utils;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ValidationUtils {

    private final Validator validator;

    public ValidationUtils(Validator validator) {
        this.validator = validator;
    }

    public void validate(Object object){
        Set<ConstraintViolation<Object>> validate = validator.validate(object);

        if (!validate.isEmpty()) throw new ConstraintViolationException(validate);
    }
}

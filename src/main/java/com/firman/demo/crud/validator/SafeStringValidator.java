package com.firman.demo.crud.validator;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

@Slf4j
public class SafeStringValidator implements ConstraintValidator<SafeStringConstraint, String> {

    private SafeStringConstraint safeString;

    @Override
    public void initialize(SafeStringConstraint safeString) {
        this.safeString = safeString;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        // default regex
        String regex = safeString.regexp();
        log.debug("value : {}", value);
        log.debug("regex : {}", regex);
        return Objects.toString(value, "").matches(regex);
    }

}

package com.firman.demo.crud.validator;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;
@Slf4j
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberConstraint, String> {

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        String phoneNumberRegex = "^(^62\\s?|^0)(\\d{3,4}?){2}\\d{3,4}$";
        return Objects.toString(phoneNumber, "").matches(phoneNumberRegex);
    }
}

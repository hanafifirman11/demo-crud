package com.firman.demo.crud.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({ METHOD, FIELD, PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = SafeStringValidator.class)
public @interface SafeStringConstraint {

    String message() default "Invalid Safe String Value";

    String regexp() default "^[a-zA-Z0-9.\\-/+,=_:'@% ]*$";

    String regexpFromProperty() default "validation.au.safeString.regex";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}

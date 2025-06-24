package br.ufscar.dc.dsw.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.ufscar.dc.dsw.validation.validator.UniquePhoneValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = UniquePhoneValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniquePhone {
    String message() default "{Unique.user.phone}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
package br.ufscar.dc.dsw.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.ufscar.dc.dsw.validation.validator.UniqueCPFValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = UniqueCPFValidator.class)
@Target(ElementType.TYPE) // ✅ Mudou para TYPE (classe) ao invés de FIELD
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueCPF {
    String message() default "CPF is already registered";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}

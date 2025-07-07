package br.ufscar.dc.dsw.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.ufscar.dc.dsw.validation.validator.ChassisPropertiesValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Anotação de validação customizada para verificar se uma string está em
 * conformidade com o formato padrão de 17 caracteres do Número de
 * Identificação do Veículo (VIN), também conhecido como chassi.
 */
@Constraint(validatedBy = ChassisPropertiesValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ChassisProperties {
    String message() default "{ChassisProperties.vehicle.chassi}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}

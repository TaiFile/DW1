package br.ufscar.dc.dsw.validation.validator;

import br.ufscar.dc.dsw.validation.ChassisProperties;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ChassisPropertiesValidator implements ConstraintValidator<ChassisProperties, String> {

    /**
     * Expressão regular que valida o formato padrão de 17 caracteres do
     * Número de Identificação do Veículo (VIN).
     * Um VIN consiste em letras maiúsculas e números.
     * As letras I, O e Q são excluídas para evitar confusão com os números 1 e 0.
     */
    private static final String CHASSIS_REGEX = "[A-HJ-NPR-Z0-9]{17}";

    @Override
    public boolean isValid(String chassis, ConstraintValidatorContext context) {
        if (chassis == null || chassis.trim().isEmpty()) {
            return true;
        }

        return chassis.toUpperCase().matches(CHASSIS_REGEX);
    }
}

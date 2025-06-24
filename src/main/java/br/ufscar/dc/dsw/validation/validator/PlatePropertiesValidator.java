package br.ufscar.dc.dsw.validation.validator;

import br.ufscar.dc.dsw.validation.PlateProperties;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PlatePropertiesValidator implements ConstraintValidator<PlateProperties, String> {

    /**
     * Expressão regular que valida os dois formatos de placa:
     * 1. Padrão antigo: 3 letras, um hífen e 4 números (ex: ABC-1234)
     * 2. Padrão Mercosul: 3 letras, 1 número, 1 letra e 2 números (ex: ABC1D23)
     */
    private static final String PLATE_REGEX = "[A-Z]{3}-?\\d[A-Z0-9]\\d{2}";

    @Override
    public boolean isValid(String plate, ConstraintValidatorContext context) {
        if (plate == null || plate.trim().isEmpty()) {
            return true;
        }
        return plate.toUpperCase().matches(PLATE_REGEX);
    }
}
package br.ufscar.dc.dsw.validation.validator;

import br.ufscar.dc.dsw.dao.IVehicleDAO;
import br.ufscar.dc.dsw.domain.Vehicle;
import br.ufscar.dc.dsw.validation.UniqueChassi;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class UniqueChassiValidator implements ConstraintValidator<UniqueChassi, Vehicle> {

    @Autowired
    private IVehicleDAO dao;

    @Override
    public boolean isValid(Vehicle vehicle, ConstraintValidatorContext context) {
        if (dao == null || vehicle == null) {
            return true;
        }

        String chassi = vehicle.getChassi();
        if (chassi == null || chassi.trim().isEmpty()) {
            return true;
        }

        Optional<Vehicle> existingVehicle = dao.findByChassi(chassi);

        if (existingVehicle.isPresent()) {
            Vehicle found = existingVehicle.get();
            if (vehicle.getId() != null && vehicle.getId().equals(found.getId())) {
                return true;
            }

            // Necessário para gerar o erro com th:errors
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("chassi")
                    .addConstraintViolation();

            return false;
        }


        return true;
    }
}
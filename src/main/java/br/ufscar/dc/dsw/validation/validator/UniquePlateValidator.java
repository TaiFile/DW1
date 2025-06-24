package br.ufscar.dc.dsw.validation.validator;

import br.ufscar.dc.dsw.dao.IVehicleDAO;
import br.ufscar.dc.dsw.domain.Vehicle;
import br.ufscar.dc.dsw.validation.UniquePlate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class UniquePlateValidator implements ConstraintValidator<UniquePlate, Vehicle> {
    @Autowired
    private IVehicleDAO dao;

    @Override
    public boolean isValid(Vehicle vehicle, ConstraintValidatorContext context) {
        if (dao == null || vehicle == null) {
            return true;
        }

        String plate = vehicle.getPlate();
        if (plate == null || plate.trim().isEmpty()) {
            return true;
        }

        Optional<Vehicle> existingVehicle = dao.findByPlate(plate);

        if (existingVehicle.isEmpty()) {
            return true;
        }

        Vehicle found = existingVehicle.get();
        if (vehicle.getId() != null && vehicle.getId().equals(found.getId())) {
            return true;
        }

        return false;
    }
}

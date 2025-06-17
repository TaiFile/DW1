package br.ufscar.dc.dsw.service.spec;

import br.ufscar.dc.dsw.domain.Vehicle;

import java.util.List;

public interface IVehicleService {
    Vehicle save(Vehicle vehicle);

    Vehicle searchById(Long id);

    List<Vehicle> searchAll();

    List<Vehicle> listById(Long id);

    Vehicle update(Vehicle vehicle);

    void delete(Long id);
}

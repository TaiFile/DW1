package br.ufscar.dc.dsw.service.spec;

import br.ufscar.dc.dsw.domain.Vehicle;

import java.util.List;

public interface IVehicleService {
    Vehicle searchById(Long id);

    List<Vehicle> searchAll();

    Vehicle save(Vehicle vehicle);

    void delete(Long id);

    Vehicle update(Vehicle vehicle);

    List<Vehicle> listById(Long id);
}

package br.ufscar.dc.dsw.service.spec;

import br.ufscar.dc.dsw.domain.Vehicle;

import java.util.List;

public interface IVehicleService {
    Vehicle searchById(Long id);

    List<Vehicle> searchAll();

    void save(Vehicle vehicle);

    void delete(Long id);

    void update(Vehicle vehicle);
}

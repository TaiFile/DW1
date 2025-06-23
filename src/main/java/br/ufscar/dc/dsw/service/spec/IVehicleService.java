package br.ufscar.dc.dsw.service.spec;

import br.ufscar.dc.dsw.domain.Vehicle;

import java.util.List;

public interface IVehicleService {
    Vehicle save(Vehicle vehicle);

    Vehicle findById(Long id);

    List<Vehicle> findAll();

    List<Vehicle> findByModelContainingIgnoreCase(String model);

    List<Vehicle> findAllByStoreId(Long id);

    Vehicle update(Vehicle vehicle);

    void delete(Long id);
}

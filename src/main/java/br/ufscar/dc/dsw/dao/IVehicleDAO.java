package br.ufscar.dc.dsw.dao;
import br.ufscar.dc.dsw.domain.Vehicle;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface IVehicleDAO extends ListCrudRepository<Vehicle, Long> {
    List<Vehicle> findAllByStoreId(Long id);

    List<Vehicle> findByModelContainingIgnoreCase(String model);
}

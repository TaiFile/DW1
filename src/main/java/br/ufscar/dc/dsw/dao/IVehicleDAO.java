package br.ufscar.dc.dsw.dao;
import br.ufscar.dc.dsw.domain.Vehicle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface IVehicleDAO extends ListCrudRepository<Vehicle, Long> {
    List<Vehicle> findAllByStoreId(Long id);

    @Query("""
            SELECT DISTINCT v
            FROM Vehicle v
            LEFT JOIN Offer o ON o.vehicle = v AND o.status = 1
            WHERE o.id IS NULL
            """)
    List<Vehicle> findAllAvailable();

    @Query("""
            SELECT DISTINCT v
            FROM Vehicle v
            LEFT JOIN Offer o ON o.vehicle = v AND o.status = 1
            WHERE o.id IS NULL
            AND LOWER(CONCAT('%',v.model,'%')) LIKE LOWER(CONCAT('%', :model, '%'))
            """)
    List<Vehicle> findAllAvailableAndByModel(String model);

    Optional<Vehicle> findByChassi(String chassi);

    Optional<Vehicle> findByPlate(String plate);
}

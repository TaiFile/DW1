package br.ufscar.dc.dsw.dao;
import br.ufscar.dc.dsw.domain.Vehicle;
import org.springframework.data.repository.ListCrudRepository;

public interface IVehicleDAO extends ListCrudRepository<Vehicle, Long> {

}

package br.ufscar.dc.dsw.service.impl;

import br.ufscar.dc.dsw.dao.IVehicleDAO;
import br.ufscar.dc.dsw.domain.Store;
import br.ufscar.dc.dsw.domain.Vehicle;
import br.ufscar.dc.dsw.exceptions.ResourceNotFoundException;
import br.ufscar.dc.dsw.service.spec.IVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class VehicleService implements IVehicleService {

    @Autowired
    IVehicleDAO dao;

    public Vehicle save(Vehicle vehicle){
       return dao.save(vehicle);
    }

    public void delete(Long id){
        dao.deleteById(id);
    }

    public Vehicle update(Vehicle vehicle){
        Vehicle entityVehicle = dao.findById(vehicle.getId()).orElseThrow(
                ()-> new ResourceNotFoundException("No records for this id"));

        entityVehicle.setId(vehicle.getId());
        entityVehicle.setPlate(vehicle.getPlate());
        entityVehicle.setModel(vehicle.getModel());
        entityVehicle.setChassi(vehicle.getChassi());
        entityVehicle.setYear(vehicle.getYear());
        entityVehicle.setMileage(vehicle.getMileage());
        entityVehicle.setDescription(vehicle.getDescription());
        entityVehicle.setValue(vehicle.getValue());

        return save(entityVehicle);
    }

    @Transactional(readOnly = true)
    public List<Vehicle> listById(Long id){
        return dao.findAllByStoreId(id);
    }

    @Transactional(readOnly = true)
    public Vehicle searchById(Long id){
        return dao.findById(id.longValue()).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Vehicle> searchAll(){
        return dao.findAll();
    }
}

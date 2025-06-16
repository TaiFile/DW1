package br.ufscar.dc.dsw.service.impl;

import br.ufscar.dc.dsw.dao.IVehicleDAO;
import br.ufscar.dc.dsw.domain.Store;
import br.ufscar.dc.dsw.domain.Vehicle;
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

    public void save(Vehicle vehicle){
        dao.save(vehicle);
    }

    public void delete(Long id){
        dao.deleteById(id);
    }

    public void update(Vehicle vehicle){

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

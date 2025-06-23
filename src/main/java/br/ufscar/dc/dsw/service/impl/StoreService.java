package br.ufscar.dc.dsw.service.impl;

import br.ufscar.dc.dsw.dao.IStoreDAO;
import br.ufscar.dc.dsw.domain.Store;
import br.ufscar.dc.dsw.exceptions.ResourceNotFoundException;
import br.ufscar.dc.dsw.service.spec.IStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StoreService implements IStoreService {

    @Autowired
    private IStoreDAO dao;

    public Store save(Store store) {
        return dao.save(store);
    }

    @Transactional(readOnly = true)
    public Store findById(Long id){
        return dao.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Store> findAll(){
        return dao.findAll();
    }

    public Store update(Store store){
        Store entityStore = dao.findById(store.getId()).orElseThrow(
                ()-> new ResourceNotFoundException("No records for this id"));

        entityStore.setEmail(store.getEmail());
        entityStore.setPassword(store.getPassword());
        entityStore.setName(store.getName());
        entityStore.setCnpj(store.getCnpj());
        entityStore.setDescription(store.getDescription());

        return dao.save(entityStore);
    }

    public void delete(Long id){
        dao.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean storeHaveVehicles(Long id) {
        Store store = dao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Store not found"));

        return store.getVehicles() != null && !store.getVehicles().isEmpty();
    }

    @Transactional(readOnly = true)
    public Store findByEmail(String email) {
        Store store = dao.findByEmail(email);
        if (store == null) {
            throw new ResourceNotFoundException("Store with email " + email + " not found");
        }
        return store;
    }
}
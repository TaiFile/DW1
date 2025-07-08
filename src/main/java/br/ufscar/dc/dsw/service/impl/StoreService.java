package br.ufscar.dc.dsw.service.impl;

import br.ufscar.dc.dsw.dao.IStoreDAO;
import br.ufscar.dc.dsw.domain.Store;
import br.ufscar.dc.dsw.exceptions.ResourceNotFoundException;
import br.ufscar.dc.dsw.service.spec.IStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StoreService implements IStoreService {

    @Autowired
    private IStoreDAO dao;

    @Override
    public List<Store> findAll() {
        return dao.findAll();
    }

    @Override
    public Optional<Store> findByEmail(String email) {
        return dao.findByEmail(email);
    }

    @Override
    public Optional<Store> findByCnpj(String cnpj) {
        return dao.findByCnpj(cnpj);
    }

    @Transactional(readOnly = true)
    public boolean storeHasVehicles(Long id) {
        Store store = dao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Store not found"));

        return store.getVehicles() != null && !store.getVehicles().isEmpty();
    }
}
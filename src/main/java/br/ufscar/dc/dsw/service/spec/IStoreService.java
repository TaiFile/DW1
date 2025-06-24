package br.ufscar.dc.dsw.service.spec;

import br.ufscar.dc.dsw.domain.Store;

import java.util.List;

public interface IStoreService {
    Store save(Store store);

    Store findById(Long id);

    List<Store> findAll();

    Store update(Store store);

    void delete(Long id);

    boolean storeHaveVehicles(Long id);

    Store findByEmail(String email);
}

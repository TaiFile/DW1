package br.ufscar.dc.dsw.service.spec;


import br.ufscar.dc.dsw.domain.Store;

import java.util.List;

public interface IStoreService {
    Store findById(Long id);

    List<Store> findAll();

    Store save(Store store);

    void delete(Long id);

    Store update(Store store);
}

package br.ufscar.dc.dsw.service.spec;


import br.ufscar.dc.dsw.domain.Store;

import java.util.List;

public interface IStoreService {
    Store searchById(Long id);

    List<Store> searchAll();

    Store save(Store store);

    void delete(Long id);

    Store update(Store store);
}

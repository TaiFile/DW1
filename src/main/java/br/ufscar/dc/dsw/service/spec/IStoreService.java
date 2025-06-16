package br.ufscar.dc.dsw.service.spec;


import br.ufscar.dc.dsw.domain.Store;

import java.util.List;

public interface IStoreService {
    Store searchById(Long id);

    List<Store> searchAll();

    void save(Store store);

    void delete(Long id);

    void update(Store store);
}

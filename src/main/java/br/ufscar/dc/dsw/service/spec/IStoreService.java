package br.ufscar.dc.dsw.service.spec;

import br.ufscar.dc.dsw.domain.Store;

import java.util.List;
import java.util.Optional;

public interface IStoreService {
    List<Store> findAll();

    Optional<Store> findByEmail(String email);

    Optional<Store> findByCnpj(String cnpj);

    boolean storeHasVehicles(Long id);
}

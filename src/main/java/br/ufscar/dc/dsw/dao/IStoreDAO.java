package br.ufscar.dc.dsw.dao;

import br.ufscar.dc.dsw.domain.Store;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface IStoreDAO extends ListCrudRepository<Store, Long> {
    Optional<Store> findByEmail(String email);

    Optional<Store> findByCnpj(String cnpj);
}

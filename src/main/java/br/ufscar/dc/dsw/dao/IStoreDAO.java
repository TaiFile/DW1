package br.ufscar.dc.dsw.dao;

import br.ufscar.dc.dsw.domain.Store;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.repository.ListCrudRepository;
import java.util.List;
import java.util.Optional;

public interface IStoreDAO extends ListCrudRepository<Store, Long> {
    Store findByEmail(String email);

    Optional<Store> findByCnpj(@NotBlank String cnpj);
}

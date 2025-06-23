package br.ufscar.dc.dsw.dao;

import br.ufscar.dc.dsw.domain.Store;
import org.springframework.data.repository.ListCrudRepository;
import java.util.List;

public interface IStoreDAO extends ListCrudRepository<Store, Long> {
    List<Store> id(Long id); // todo: (jonatã) esse nome de método não faz sentido

    Store findByEmail(String email);
}

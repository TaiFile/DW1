package br.ufscar.dc.dsw.dao;

import br.ufscar.dc.dsw.domain.Client;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface IClientDAO extends ListCrudRepository<Client, Long> {
    Optional<Client> findByEmail(String email);
}
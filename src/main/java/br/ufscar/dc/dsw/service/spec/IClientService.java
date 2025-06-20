package br.ufscar.dc.dsw.service.spec;

import br.ufscar.dc.dsw.domain.Client;

import java.util.List;

public interface IClientService {
    Client save(Client client);

    Client findById(Long id);

    List<Client> findAll();

    Client update(Client client);

    void delete(Long id);
}

package br.ufscar.dc.dsw.service.spec;

import br.ufscar.dc.dsw.domain.Client;

import java.util.List;

public interface IClientService {
    Client searchById(Long id);

    List<Client> searchAll();

    void save(Client client);

    void delete(Long id);

    void update(Client client);
}

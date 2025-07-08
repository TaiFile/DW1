package br.ufscar.dc.dsw.service.impl;

import br.ufscar.dc.dsw.dao.IClientDAO;
import br.ufscar.dc.dsw.domain.Client;
import br.ufscar.dc.dsw.service.spec.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService implements IClientService {

    @Autowired
    private IClientDAO dao;

    @Override
    public List<Client> findAll() {
        return dao.findAll();
    }

    @Override
    public Optional<Client> findByEmail(String email) {
        return dao.findByEmail(email);
    }

    @Override
    public Optional<Client> findByPhone(String phone) {
        return dao.findByPhone(phone);
    }

    @Override
    public Optional<Client> findByCpf(String cpf) {
        return dao.findByCpf(cpf);
    }
}

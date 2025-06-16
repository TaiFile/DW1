package br.ufscar.dc.dsw.service.impl;

import br.ufscar.dc.dsw.dao.IClientDAO;
import br.ufscar.dc.dsw.domain.Client;
import br.ufscar.dc.dsw.service.spec.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ClientService implements IClientService {

    @Autowired
    IClientDAO dao;

    public void save(Client client){
        dao.save(client);
    }

    public void delete(Long id){
        dao.deleteById(id);
    }

    public void update(Client client){

    }

    @Transactional(readOnly = true)
    public Client searchById(Long id){
        return dao.findById(id.longValue()).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Client> searchAll(){
        return dao.findAll();
    }
}

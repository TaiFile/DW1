package br.ufscar.dc.dsw.service.impl;

import br.ufscar.dc.dsw.dao.IClientDAO;
import br.ufscar.dc.dsw.domain.Client;
import br.ufscar.dc.dsw.exceptions.ResourceNotFoundException;
import br.ufscar.dc.dsw.service.spec.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClientService implements IClientService {

    @Autowired
    IClientDAO dao;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public Client save(Client client){
        return dao.save(client);
    }

    @Transactional(readOnly = true)
    public Client findById(Long id){
        return dao.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public Client findByEmail(String email){
        return dao.findByEmail(email).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Client> findAll(){
        return dao.findAll();
    }

    public Client update(Client client){
        Client entityClient = dao.findById(client.getId()).orElseThrow(
                ()-> new ResourceNotFoundException("No records for this id"));

        entityClient.setEmail(client.getEmail());
        entityClient.setPassword(encoder.encode(client.getPassword()));
        entityClient.setCpf(client.getCpf());
        entityClient.setName(client.getName());
        entityClient.setPhone(client.getPhone());
        entityClient.setSex(client.getSex());
        entityClient.setDateOfBirth(client.getDateOfBirth());

        return dao.save(entityClient);
    }

    public void delete(Long id){
        dao.deleteById(id);
    }
}

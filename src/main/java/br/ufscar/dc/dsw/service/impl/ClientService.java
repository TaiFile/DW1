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

    public Client update(Client clientFromForm) {
        Client entityClient = dao.findById(clientFromForm.getId()).orElseThrow(
                () -> new ResourceNotFoundException("No records for this id"));

        entityClient.setEmail(clientFromForm.getEmail());
        entityClient.setCpf(clientFromForm.getCpf());
        String newPassword = clientFromForm.getPassword();
        if (newPassword != null && !newPassword.isEmpty()) {
            entityClient.setPassword(newPassword);
        }

        return dao.save(entityClient);
    }

    public void delete(Long id){
        dao.deleteById(id);
    }
}

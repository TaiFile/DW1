package br.ufscar.dc.dsw.service.impl;

import br.ufscar.dc.dsw.dao.IStoreDAO;
import br.ufscar.dc.dsw.domain.Store;
import br.ufscar.dc.dsw.service.spec.IStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = false)
public class StoreService implements IStoreService {

    @Autowired
    IStoreDAO dao;

    public void save(Store store) {
        dao.save(store);
    }

    public void delete(Long id){
        dao.deleteById(id);
    }

    public void update(Store store){

    }

    @Transactional(readOnly = true)
    public Store searchById(Long id){
        return dao.findById(id.longValue()).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Store> searchAll(){
        return dao.findAll();
    }
}

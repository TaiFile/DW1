package br.ufscar.dc.dsw.service.impl;

import br.ufscar.dc.dsw.dao.IOfferDAO;
import br.ufscar.dc.dsw.domain.Offer;
import br.ufscar.dc.dsw.domain.enums.OfferStatus;
import br.ufscar.dc.dsw.exceptions.ResourceNotFoundException;
import br.ufscar.dc.dsw.service.spec.IOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OfferService implements IOfferService {

    @Autowired
    private IOfferDAO dao;

    public Offer save(Offer offer) {
        return dao.save(offer);
    }

    @Transactional(readOnly = true)
    public List<Offer> findAll() {
        return dao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Offer> findAllByClientId(Long id) {
        return dao.findAllByClientId(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Offer> findAllByClientEmail(String email) {
        return dao.findAllByClientEmail(email);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Offer> findAllByStoreId(Long id) {
        return dao.findAllByStoreId(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Offer> findAllByStoreEmail(String email) {
        return dao.findAllByStoreEmail(email);
    }

    @Transactional(readOnly = true)
    @Override
    public Offer findById(Long id) {
        return dao.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Offer not found for this id" + id)
        );
    }

    @Override
    public Offer update(Offer offer) {
        Offer entityOffer = dao.findById(offer.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Offer not found for this id" + offer.getId())
        );

        entityOffer.setValue(offer.getValue());
        entityOffer.setDate(offer.getDate());
        entityOffer.setStatus(offer.getStatus());

        return dao.save(entityOffer);
    }

    @Override
    public void delete(Long id) {
        dao.deleteById(id);
    }
}

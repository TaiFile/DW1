package br.ufscar.dc.dsw.service.spec;

import br.ufscar.dc.dsw.domain.Offer;

import java.util.List;

public interface IOfferService {
    Offer save(Offer offer);

    List<Offer> findAll();

    List<Offer> findAllByClientId(Long clientId);

    List<Offer> findAllByStoreId(Long storeId);

    Offer findById(Long id);

    Offer update(Offer offer);

    void delete(Long id);
}

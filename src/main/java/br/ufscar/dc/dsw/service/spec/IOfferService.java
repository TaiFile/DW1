package br.ufscar.dc.dsw.service.spec;

import br.ufscar.dc.dsw.domain.Offer;
import br.ufscar.dc.dsw.domain.enums.OfferStatus;

import java.util.List;

public interface IOfferService {
    Offer save(Offer offer);

    List<Offer> findAll();

    List<Offer> findAllByClientIdAndStatus(Long id, OfferStatus status);

    Offer findById(Long id);

    Offer update(Offer offer);

    void delete(Long id);
}

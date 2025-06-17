package br.ufscar.dc.dsw.dao;

import br.ufscar.dc.dsw.domain.Offer;
import br.ufscar.dc.dsw.domain.enums.OfferStatus;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface IOfferDAO extends ListCrudRepository<Offer, Long> {
    List<Offer> findAllByClientIdAndStatus(Long id, OfferStatus status);
}
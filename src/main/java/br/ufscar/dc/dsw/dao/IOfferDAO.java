package br.ufscar.dc.dsw.dao;

import br.ufscar.dc.dsw.domain.Offer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface IOfferDAO extends ListCrudRepository<Offer, Long> {
    List<Offer> findAllByClientId(Long clientId);

    @Query("SELECT o FROM Offer o WHERE o.vehicle.store.id = :storeId")
    List<Offer> findAllByStoreId(Long storeId);
}
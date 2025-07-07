package br.ufscar.dc.dsw.service.spec;

import br.ufscar.dc.dsw.domain.Offer;

import java.util.List;

public interface IOfferService {
    Offer save(Offer offer);

    List<Offer> findAll();

    List<Offer> findAllByVehicleId(Long vehicleId);

    List<Offer> findAllByClientId(Long clientId);

    List<Offer> findAllByClientEmail(String clientEmail);

    List<Offer> findAllByStoreId(Long storeId);

    List<Offer> findAllByStoreEmail(String storeEmail);

    Offer findById(Long id);

    Offer update(Offer offer);

    void delete(Long id);
}

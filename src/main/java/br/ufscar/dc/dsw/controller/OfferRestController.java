package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.domain.Offer;
import br.ufscar.dc.dsw.service.spec.IOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
public class OfferRestController {

    @Autowired
    private IOfferService offerService;

    @GetMapping("/vehicles/{id}")
    public ResponseEntity<List<Offer>> listByVehicle(@PathVariable Long id) {

        List<Offer> propostas = offerService.findAllByVehicleId(id);
        if (propostas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(propostas);
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<List<Offer>> listByClients(@PathVariable Long id) {

        List<Offer> propostas = offerService.findAllByClientId(id);
        if (propostas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(propostas);
    }
}
package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.domain.Store;
import br.ufscar.dc.dsw.domain.Vehicle;
import br.ufscar.dc.dsw.service.spec.IStoreService;
import br.ufscar.dc.dsw.service.spec.IVehicleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleRestController {

    @Autowired
    private IVehicleService vehicleService;

    @Autowired
    private IStoreService storeService;

    @GetMapping("/models/{model}")
    public ResponseEntity<List<Vehicle>> listByModel(@PathVariable("model") String model) {

        List<Vehicle> vehicles = vehicleService.findAllAvailableAndByModel(model);
        if (vehicles.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/stores/{storeId}")
    public ResponseEntity<List<Vehicle>> listByStore(@PathVariable("storeId") Long storeId) {

        List<Vehicle> vehicles = vehicleService.findAllByStoreId(storeId);
        if (vehicles.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(vehicles);
    }

    @PostMapping("/stores/{storeId}")
    @ResponseBody
    public ResponseEntity<Vehicle> create(@Valid @RequestBody Vehicle vehicle,
                                          BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(null);
        }

        Store store = storeService.findById(vehicle.getStore().getId());
        if (store == null) {
            return ResponseEntity.notFound().build();
        }

        vehicle.setStore(store);

        Vehicle savedVehicle = vehicleService.save(vehicle);
        return ResponseEntity.ok(savedVehicle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> update(@PathVariable("id") Long id,
                                          @Valid @RequestBody Vehicle vehicle,
                                          BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(null);
        }

        Vehicle existingVehicle = vehicleService.findById(id);
        if (existingVehicle == null) {
            return ResponseEntity.notFound().build();
        }

        vehicle.setId(id);
        Vehicle updatedVehicle = vehicleService.update(vehicle);
        return ResponseEntity.ok(updatedVehicle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {

        Vehicle vehicle = vehicleService.findById(id);
        if (vehicle == null) {
            return ResponseEntity.notFound().build();
        }

        vehicleService.delete(id);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
}
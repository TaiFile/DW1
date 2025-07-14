package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.domain.Store;
import br.ufscar.dc.dsw.service.spec.IStoreService;
import br.ufscar.dc.dsw.service.spec.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
public class StoreRestController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IStoreService storeService;

    @GetMapping
    public ResponseEntity<List<Store>> listStores() {

        List<Store> listStores = storeService.findAll();
        if (listStores.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listStores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Store> listByID(@PathVariable("id") Long id) {

        Store store = storeService.findById(id);
        if (store == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(store);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Store> create(@Valid @RequestBody Store store, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(null);
        }

        if (storeService.findByCnpj(store.getCnpj()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        if (userService.findByEmail(store.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        Store savedStore = (Store) userService.save(store);
        return ResponseEntity.ok(savedStore);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Store> update(@PathVariable("id") Long id,
                                        @Valid @RequestBody Store store,
                                        BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(null);
        }

        Store existingStore = storeService.findById(id);
        if (existingStore == null) {
            return ResponseEntity.notFound().build();
        }
        if (!existingStore.getEmail().equals(store.getEmail())) {
            if (userService.findByEmail(store.getEmail()) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }
        }

        store.setId(id);
        Store updatedStore = (Store) userService.update(store);
        return ResponseEntity.ok(updatedStore);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {

        Store store = storeService.findById(id);
        if (store == null) {
            return ResponseEntity.notFound().build();
        }

        if (storeService.storeHasVehicles(id)) {
            return new ResponseEntity<Boolean>(false, HttpStatus.FORBIDDEN);
        }

        userService.delete(id);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
}

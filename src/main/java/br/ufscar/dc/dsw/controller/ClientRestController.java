package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.domain.Client;
import br.ufscar.dc.dsw.service.spec.IClientService;
import br.ufscar.dc.dsw.service.spec.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientRestController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IClientService clientService;

    @GetMapping
    public ResponseEntity<List<Client>> listClients() {

        List<Client> listClients = clientService.findAll();
        if (listClients.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listClients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> listByID(@PathVariable("id") Long id) {

        Client client = clientService.findById(id);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(client);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Client> create(@Valid @RequestBody Client client, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(null);
        }

        if (clientService.findByCpf(client.getCpf()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        if (userService.findByEmail(client.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        Client savedClient = (Client) userService.save(client);
        return ResponseEntity.ok(savedClient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> update(@PathVariable("id") Long id,
                                         @Valid @RequestBody Client client,
                                         BindingResult result) {

        if (result.getFieldErrorCount() > 1 || result.getFieldError("cpf") == null) {
            return ResponseEntity.badRequest().body(null);
        }

        Client existingClient = clientService.findById(id);
        if (existingClient == null) {
            return ResponseEntity.notFound().build();
        }
        if (!existingClient.getEmail().equals(client.getEmail())) {
            if (userService.findByEmail(client.getEmail()) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }
        }

        client.setId(id);
        Client updatedClient = (Client) userService.update(client);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {

        Client client = clientService.findById(id);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }

        userService.delete(id);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
}
package br.ufscar.dc.dsw.service.spec;

import br.ufscar.dc.dsw.domain.Client;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Optional;

public interface IClientService {
    List<Client> findAll();

    Client findById(Long id);

    Optional<Client> findByEmail(String email);

    Optional<Client> findByPhone(String phone);

    Optional<Client> findByCpf(@NotBlank String cpf);
}

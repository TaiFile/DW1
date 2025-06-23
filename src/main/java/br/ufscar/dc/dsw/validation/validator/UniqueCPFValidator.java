package br.ufscar.dc.dsw.validation.validator;

import br.ufscar.dc.dsw.dao.IClientDAO;
import br.ufscar.dc.dsw.domain.Client;
import br.ufscar.dc.dsw.validation.UniqueCPF;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UniqueCPFValidator implements ConstraintValidator<UniqueCPF, Client> {

    @Autowired
    private IClientDAO dao;

    @Override
    public boolean isValid(Client client, ConstraintValidatorContext context) {
        if (dao == null || client == null) {
            return true; // Se dao é null ou client é null, considera válido
        }

        String cpf = client.getCpf();
        if (cpf == null || cpf.trim().isEmpty()) {
            return true; // Deixa outras validações cuidarem de CPF nulo/vazio
        }

        Optional<Client> existingClient = dao.findByCpf(cpf);

        // Se não encontrou nenhum cliente com esse CPF, é único
        if (existingClient.isEmpty()) {
            return true;
        }

        // Se encontrou, verifica se é o mesmo cliente (UPDATE)
        Client found = existingClient.get();
        if (client.getId() != null && client.getId().equals(found.getId())) {
            return true; // É o mesmo cliente, pode manter o CPF
        }

        return false; // CPF já existe em outro cliente
    }
}

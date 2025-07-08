package br.ufscar.dc.dsw.validation.validator;

import br.ufscar.dc.dsw.domain.Client;
import br.ufscar.dc.dsw.service.spec.IClientService;
import br.ufscar.dc.dsw.validation.UniqueCPF;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UniqueCPFValidator implements ConstraintValidator<UniqueCPF, Client> {

    @Autowired
    private IClientService clientService;

    @Override
    public boolean isValid(Client client, ConstraintValidatorContext context) {
        if (clientService == null || client == null) {
            return true;
        }

        String cpf = client.getCpf();
        if (cpf == null || cpf.trim().isEmpty()) {
            return true;
        }

        Optional<Client> existingClient = clientService.findByCpf(cpf);

        if (existingClient.isPresent()) {
            Client found = existingClient.get();
            if (client.getId() != null && client.getId().equals(found.getId())) {
                return true;
            }

            // Necessário para gerar o erro com th:errors
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("cpf")
                    .addConstraintViolation();

            return false;
        }

        return true;
    }
}
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
            return true;
        }

        String cpf = client.getCpf();
        if (cpf == null || cpf.trim().isEmpty()) {
            return true;
        }

        Optional<Client> existingClient = dao.findByCpf(cpf);

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
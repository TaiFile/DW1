package br.ufscar.dc.dsw.validation.validator;

import br.ufscar.dc.dsw.dao.IClientDAO;
import br.ufscar.dc.dsw.domain.Client;
import br.ufscar.dc.dsw.validation.UniquePhone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UniquePhoneValidator implements ConstraintValidator<UniquePhone, Client> {

    @Autowired
    private IClientDAO dao;

    @Override
    public boolean isValid(Client client, ConstraintValidatorContext context) {
        if (dao == null || client == null) {
            return true;
        }

        String phone = client.getPhone();
        if (phone == null || phone.trim().isEmpty()) {
            return true;
        }

        Optional<Client> existingClient = dao.findByPhone(phone);
        if (existingClient.isEmpty()) {
            return true;
        }

        Client found = existingClient.get();
        if (client.getId() != null && client.getId().equals(found.getId())) {
            return true;
        }

        return false;
    }
}

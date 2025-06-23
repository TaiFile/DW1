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
public class UniquePhoneValidator implements ConstraintValidator<UniquePhone, String> {

    @Autowired
    private IClientDAO dao;

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        // Se o DAO não estiver disponível, assume válido
        if (dao == null) {
            return true;
        }

        // Se o telefone for null ou vazio, deixa outras validações cuidarem
        if (phone == null || phone.trim().isEmpty()) {
            return true;
        }

        Optional<Client> client = dao.findByPhone(phone);

        // Se não encontrou cliente com esse telefone, é único (válido)
        // Se encontrou cliente com esse telefone, não é único (inválido)
        return client.isEmpty();
    }
}

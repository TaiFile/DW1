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
        if (dao != null) {
            Optional<Client> client = dao.findByPhone(phone);
            if(client.isEmpty()) {
                return false;
            }
        } else {
            return true;
        }
        return false;
    }
}
package br.ufscar.dc.dsw.validation.validator;

import br.ufscar.dc.dsw.dao.IClientDAO;
import br.ufscar.dc.dsw.dao.IUserDAO;
import br.ufscar.dc.dsw.domain.Client;
import br.ufscar.dc.dsw.domain.User;
import br.ufscar.dc.dsw.validation.UniqueEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private IUserDAO dao;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (dao == null) {
            return true; // Se dao é null, considera válido
        }

        if (email == null || email.trim().isEmpty()) {
            return true; // Deixa outras validações cuidarem de email nulo/vazio
        }

        Optional<User> user = dao.findByEmail(email);

        // Retorna true se NÃO encontrou cliente (email único)
        // Retorna false se encontrou cliente (email duplicado)
        return user.isEmpty();
    }
}

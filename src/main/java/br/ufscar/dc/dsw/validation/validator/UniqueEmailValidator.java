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
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, User> {

    @Autowired
    private IUserDAO dao;

    @Override
    public boolean isValid(User user, ConstraintValidatorContext context) {
        if (dao == null || user == null) {
            return true;
        }

        String email = user.getEmail();
        if (email == null || email.trim().isEmpty()) {
            return true;
        }

        Optional<User> existingUser = dao.findByEmail(email);

        // Se não encontrou nenhum cliente com esse email, é único
        if (existingUser.isEmpty()) {
            return true;
        }

        // Se encontrou, verifica se é o mesmo cliente (UPDATE)
        User found = existingUser.get();
        if (user.getId() != null && user.getId().equals(found.getId())) {
            return true; // É o mesmo cliente, pode manter o email
        }

        return false; // Email já existe em outro cliente
    }
}

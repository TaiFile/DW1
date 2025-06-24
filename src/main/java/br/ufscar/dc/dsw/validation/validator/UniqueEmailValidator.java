package br.ufscar.dc.dsw.validation.validator;

import br.ufscar.dc.dsw.dao.IUserDAO;
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

        if (existingUser.isPresent()) {
            User found = existingUser.get();
            if (user.getId() != null && user.getId().equals(found.getId())) {
                return true;
            }

            // Necessário para gerar o erro com th:errors
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("email")
                    .addConstraintViolation();

            return false;
        }

        return true;
    }
}
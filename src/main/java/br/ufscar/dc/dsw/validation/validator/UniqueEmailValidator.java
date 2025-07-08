package br.ufscar.dc.dsw.validation.validator;

import br.ufscar.dc.dsw.domain.User;
import br.ufscar.dc.dsw.service.spec.IUserService;
import br.ufscar.dc.dsw.validation.UniqueEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, User> {

    @Autowired
    private IUserService userService;

    @Override
    public boolean isValid(User user, ConstraintValidatorContext context) {
        if (userService == null || user == null) {
            return true;
        }

        String email = user.getEmail();
        if (email == null || email.trim().isEmpty()) {
            return true;
        }

        User userFound = userService.findByEmail(email);

        if (userFound != null) {
            if (user.getId() != null && user.getId().equals(userFound.getId())) {
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
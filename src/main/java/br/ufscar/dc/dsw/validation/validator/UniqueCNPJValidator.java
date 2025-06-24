package br.ufscar.dc.dsw.validation.validator;

import br.ufscar.dc.dsw.dao.IStoreDAO;
import br.ufscar.dc.dsw.domain.Store;
import br.ufscar.dc.dsw.validation.UniqueCNPJ;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class UniqueCNPJValidator implements ConstraintValidator<UniqueCNPJ, Store> {

    @Autowired
    private IStoreDAO dao;

    @Override
    public boolean isValid(Store store, ConstraintValidatorContext context) {
        if (dao == null || store == null) {
            return true;
        }

        String cnpj = store.getCnpj();
        if (cnpj == null || cnpj.trim().isEmpty()) {
            return true;
        }

        Optional<Store> existingStore = dao.findByCnpj(cnpj);

        if (existingStore.isPresent()) {
            Store found = existingStore.get();
            if (store.getId() != null && store.getId().equals(found.getId())) {
                return true;
            }

            // Necessário para gerar o erro com th:errors
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("cnpj")
                    .addConstraintViolation();

            return false;
        }

        return true;
    }
}
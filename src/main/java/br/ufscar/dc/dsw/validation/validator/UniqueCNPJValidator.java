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
public class UniqueCNPJValidator implements ConstraintValidator<UniqueCNPJ, String> {

    @Autowired
    private IStoreDAO dao;

    @Override
    public boolean isValid(String CNPJ, ConstraintValidatorContext context) {
        if (dao != null) {
            Optional<Store> store = dao.findByCnpj(CNPJ);
            if(store.isEmpty()) {
                return false;
            }
        } else {
            return true;
        }
        return false;
    }
}
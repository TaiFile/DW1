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
        // Se o DAO não estiver disponível, assume válido
        if (dao == null) {
            return true;
        }

        // Se o CNPJ for null ou vazio, deixa outras validações cuidarem
        if (CNPJ == null || CNPJ.trim().isEmpty()) {
            return true;
        }

        Optional<Store> store = dao.findByCnpj(CNPJ);

        // ✅ LÓGICA CORRETA:
        // Se NÃO encontrou store = CNPJ único = válido (true)
        // Se encontrou store = CNPJ duplicado = inválido (false)
        return store.isEmpty();
    }
}

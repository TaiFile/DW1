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
        // Se o DAO não estiver disponível, assume válido
        if (dao == null || store == null) {
            return true;
        }

        String cnpj = store.getCnpj();
        // Se o CNPJ for null ou vazio, deixa outras validações cuidarem
        if (cnpj == null || cnpj.trim().isEmpty()) {
            return true;
        }

        Optional<Store> existingStore = dao.findByCnpj(cnpj);

        // Se não encontrou nenhuma store com esse CNPJ, é único
        if (existingStore.isEmpty()) {
            return true;
        }

        // Se encontrou, verifica se é a mesma store (UPDATE)
        Store found = existingStore.get();
        if (store.getId() != null && store.getId().equals(found.getId())) {
            return true; // É a mesma store, pode manter o CNPJ
        }

        return false; // CNPJ já existe em outra store
    }
}

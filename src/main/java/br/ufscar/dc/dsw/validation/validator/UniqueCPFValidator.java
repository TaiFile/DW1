package br.ufscar.dc.dsw.validation.validator;

import br.ufscar.dc.dsw.dao.IClientDAO;
import br.ufscar.dc.dsw.domain.Client;
import br.ufscar.dc.dsw.validation.UniqueCPF;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UniqueCPFValidator implements ConstraintValidator<UniqueCPF, String> {

    @Autowired
    private IClientDAO dao;

    @Override
    public boolean isValid(String CPF, ConstraintValidatorContext context) {
        if (dao == null) {
            return true; // Se dao é null, considera válido
        }

        if (CPF == null || CPF.trim().isEmpty()) {
            return true; // Deixa outras validações cuidarem de CPF nulo/vazio
        }

        Optional<Client> client = dao.findByCpf(CPF);

        // Retorna true se NÃO encontrou cliente (CPF único)
        // Retorna false se encontrou cliente (CPF duplicado)
        return client.isEmpty();
    }
}

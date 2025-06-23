 package br.ufscar.dc.dsw.validation.validator;

 import br.ufscar.dc.dsw.dao.IUserDAO;
 import br.ufscar.dc.dsw.domain.User;
 import br.ufscar.dc.dsw.validation.UniqueCPF;
 import jakarta.validation.ConstraintValidator;
 import jakarta.validation.ConstraintValidatorContext;

 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Component;

 import java.util.Optional;

 @Component
 public class UniqueCPFValidator implements ConstraintValidator<UniqueCPF, String> {

     @Autowired
     private IUserDAO dao;

     @Override
     public boolean isValid(String CPF, ConstraintValidatorContext context) {
         if (dao != null) {
             Optional<User> user = dao.findByCPF(CPF);
             if(user.isEmpty()) {
                 return false;
             }
         } else {
            return true;
         }
         return false;
     }
 }
// package br.ufscar.dc.dsw.validation;

// import jakarta.validation.ConstraintValidator;
// import jakarta.validation.ConstraintValidatorContext;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;

// @Component
// public class UniqueCNPJValidator implements ConstraintValidator<UniqueCNPJ, String> {

//     @Autowired
//     private IEditoraDAO dao;

//     @Override
//     public boolean isValid(String CNPJ, ConstraintValidatorContext context) {
//         if (dao != null) {
//             Editora editora = dao.findByCNPJ(CNPJ);
//             return editora == null;
//         } else {
//             Durante a execução da classe LivrariaMvcApplication
//             não há injeção de dependência
//            return true;
//         }

//     }
// }
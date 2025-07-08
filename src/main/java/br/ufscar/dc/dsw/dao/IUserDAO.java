package br.ufscar.dc.dsw.dao;

import br.ufscar.dc.dsw.domain.User;
import br.ufscar.dc.dsw.domain.enums.UserRoleEnum;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface IUserDAO extends ListCrudRepository<User, Long> {
    Optional<User> findByEmail(String username);

    List<User> findAllByRole(UserRoleEnum role);
}
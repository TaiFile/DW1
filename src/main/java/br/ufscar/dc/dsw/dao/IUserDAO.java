package br.ufscar.dc.dsw.dao;

import br.ufscar.dc.dsw.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IUserDAO extends ListCrudRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.email = :username")
    Optional<User> findByEmail(@Param("username") String username);
}
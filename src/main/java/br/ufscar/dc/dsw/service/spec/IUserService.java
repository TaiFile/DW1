package br.ufscar.dc.dsw.service.spec;

import br.ufscar.dc.dsw.domain.User;

public interface IUserService {
    User save(User user);

    User findById(Long id);

    User findByEmail(String email);

    User update(User user);

    void delete(Long id);
}

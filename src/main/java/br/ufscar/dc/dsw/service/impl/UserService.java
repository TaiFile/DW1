package br.ufscar.dc.dsw.service.impl;

import br.ufscar.dc.dsw.dao.IUserDAO;
import br.ufscar.dc.dsw.domain.User;
import br.ufscar.dc.dsw.domain.enums.UserRoleEnum;
import br.ufscar.dc.dsw.service.spec.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserDAO dao;

    @Override
    public User save(User user) {
        return dao.save(user);
    }

    @Override
    public User findById(Long id) {
        return dao.findById(id).orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        return dao.findByEmail(email).orElse(null);
    }

    @Override
    public User update(User user) {
        User userToUpdate = dao.findById(user.getId()).orElse(null);

        if (userToUpdate != null) {
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setPassword(user.getPassword());
            userToUpdate.setRole(user.getRole());
            userToUpdate.setEnabled(user.isEnabled());
        }

        return userToUpdate;
    }

    @Override
    public void delete(Long id) {
        dao.deleteById(id);
    }
}

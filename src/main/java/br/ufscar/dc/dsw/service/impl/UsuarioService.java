package br.ufscar.dc.dsw.service.impl;

import java.util.List;

import br.ufscar.dc.dsw.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.dao.IUsuarioDAO;
import br.ufscar.dc.dsw.domain.User;
import br.ufscar.dc.dsw.service.spec.IUsuarioService;

@Service
@Transactional(readOnly = false)
public class UsuarioService implements IUsuarioService {

    @Autowired
    IUsuarioDAO dao;

    public void salvar(User user) {
        dao.save(user);
    }

    public void excluir(Long id) {
        dao.deleteById(id);
    }

    @Transactional(readOnly = true)
    public User buscarPorId(Long id) {
        return dao.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Usuário não encontrado"));
    }

    @Transactional(readOnly = true)
    public List<User> buscarTodos() {
        return dao.findAll();
    }
}
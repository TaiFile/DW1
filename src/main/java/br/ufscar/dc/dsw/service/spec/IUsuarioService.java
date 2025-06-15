package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.User;

public interface IUsuarioService {

    User buscarPorId(Long id);

    List<User> buscarTodos();

    void salvar(User editora);

    void excluir(Long id);
}
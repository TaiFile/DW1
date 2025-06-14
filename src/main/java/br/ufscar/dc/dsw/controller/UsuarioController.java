package br.ufscar.dc.dsw.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.domain.User;
import br.ufscar.dc.dsw.service.spec.IUsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private IUsuarioService service;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping("/cadastrar")
    public String cadastrar(User user) {
        return "usuario/cadastro";
    }

    @GetMapping("/listar")
    public String listar(ModelMap model) {
        model.addAttribute("usuarios",service.buscarTodos());
        return "usuario/lista";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid User user, BindingResult result, RedirectAttributes attr) {

        if (result.hasErrors()) {
            return "usuario/cadastro";
        }

        System.out.println("password = " + user.getPassword());
        user.setPassword(encoder.encode(user.getPassword()));
        service.salvar(user);
        attr.addFlashAttribute("sucess", "usuario.create.sucess");
        return "redirect:/usuarios/listar";
    }

    @GetMapping("/editar/{id}")
    public String preEditar(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("usuario", service.buscarPorId(id));
        return "usuario/cadastro";
    }

    @PostMapping("/editar")
    public String editar(@Valid User user, String novoPassword, BindingResult result, RedirectAttributes attr) {

        if (result.hasErrors()) {
            return "usuario/cadastro";
        }

        if (novoPassword != null && !novoPassword.trim().isEmpty()) {
            user.setPassword(encoder.encode(novoPassword));
        } else {
            System.out.println("Senha não foi editada");
        }
        service.salvar(user);
        attr.addFlashAttribute("sucess", "usuario.edit.sucess");
        return "redirect:/usuarios/listar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, ModelMap model) {
        service.excluir(id);
        model.addAttribute("sucess", "usuario.delete.sucess");
        return listar(model);
    }
}
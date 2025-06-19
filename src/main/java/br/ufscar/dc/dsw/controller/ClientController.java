package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.domain.Client;
import br.ufscar.dc.dsw.service.spec.IClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private IClientService clientService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping("/register")
    public String register(Client client) {
        return "client/register";
    }

    @GetMapping("/list")
    public String list(ModelMap model) {
        model.addAttribute("client", clientService.findAll());
        return "client/list";
    }

    @PostMapping("/save")
    public String save(@Valid Client client, BindingResult result, RedirectAttributes attributes) {
        if(result.hasErrors()) {
            return "client/register";
        }

        System.out.println("password = " + client.getPassword());
        client.setPassword(encoder.encode(client.getPassword()));
        clientService.save(client);
        attributes.addFlashAttribute("sucess", "client.create.sucess");
        return "redirect:/client/list";
    }

    @GetMapping("/edit/{id}")
    public String preEdit(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("client", clientService.findById(id));
        return "client/register";
    }

    @PostMapping("/edit")
    public String edit(@Valid Client client, String newPassword,BindingResult result, RedirectAttributes attributes) {
        if(result.hasErrors()) {
            return "client/register";
        }

        if(newPassword != null && !newPassword.trim().isEmpty()) {
            client.setPassword(encoder.encode(newPassword));
        } else {
            System.out.println("Password was not edited");
        }

        clientService.update(client);
        attributes.addFlashAttribute("sucess", "client.edit.sucess");
        return "redirect:/client/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, ModelMap model) {
        clientService.delete(id);
        model.addAttribute("sucess", "client.delete.sucess");
        return list(model);
    }
}

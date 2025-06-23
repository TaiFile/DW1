package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.domain.Client;
import br.ufscar.dc.dsw.service.spec.IClientService;
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

    @PostMapping("/save")
    public String save(@Valid Client client, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
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
        return "client/registerUpdate"; // ✅ Era "client/register", agora "client/edit"
    }

    @PostMapping("/edit")
    public String edit(@Valid Client client, String newPassword, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "client/registerUpdate"; // ✅ Era "client/register", agora "client/edit"
        }

        try {
            if (newPassword != null && !newPassword.trim().isEmpty()) {
                client.setPassword(encoder.encode(newPassword));
            } else {
                System.out.println("Password was not edited");
            }

            clientService.update(client);
            attributes.addFlashAttribute("sucess", "Cliente atualizado com sucesso!");
            return "redirect:/admin/client/list"; // ✅ Verificar se é esta a rota correta

        } catch (Exception e) {
            attributes.addFlashAttribute("fail", "Erro ao atualizar cliente!");
            return "client/registerUpdate";
        }
    }


    @GetMapping("/offer")
    public String offer(ModelMap model) {
        return "client/offerList";
    }


    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes attributes) {
        try {
            clientService.delete(id);
            attributes.addFlashAttribute("sucess", "Cliente excluído com sucesso!");
            return "redirect:/admin/client/list"; // ✅ Era /admin/store/list
        } catch (Exception e) {
            attributes.addFlashAttribute("fail", "Erro ao excluir cliente!");
            return "redirect:/admin/client/list";
        }
    }
}

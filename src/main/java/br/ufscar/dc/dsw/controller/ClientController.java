package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.domain.Client;
import br.ufscar.dc.dsw.service.spec.IClientService;
import br.ufscar.dc.dsw.service.spec.IOfferService;
import br.ufscar.dc.dsw.service.spec.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IClientService clientService;

    @Autowired
    private IOfferService offerService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping("/register")
    public String showRegisterClientForm(Model model) {
        model.addAttribute("client", new Client());
        return "client/register";
    }

    @PostMapping("/register")
    public String registerClient(@Valid Client client, BindingResult result, RedirectAttributes attributes, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("client", client);
            return "client/register";
        }

        client.setPassword(encoder.encode(client.getPassword()));

        try {
            userService.save(client);
        } catch (Exception e) {
            model.addAttribute("client", client);
            model.addAttribute("errorMessage", "Erro interno do servidor. Tente novamente.");
            return "client/register";
        }

        attributes.addFlashAttribute("success", "Cliente cadastrado com sucesso!");
        return "redirect:/admin/home";
    }

    @GetMapping("/list")
    public String listClients(Model model) {
        List<Client> clients = clientService.findAll();
        model.addAttribute("clients", clients);
        return "client/list";
    }

    @GetMapping("/edit/{id}")
    public String preEdit(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("client", userService.findById(id));
        return "client/update";
    }

    @PostMapping("/edit")
    public String edit(@Valid Client client, BindingResult result, RedirectAttributes attributes, ModelMap model) {
        if (result.hasErrors()) {
            model.addAttribute("client", client);
            return "client/update";
        }

        try {
            if (client.getPassword() != null && !client.getPassword().trim().isEmpty()) {
                client.setPassword(encoder.encode(client.getPassword()));
            }

            userService.update(client);
            attributes.addFlashAttribute("sucess", "Cliente atualizado com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao atualizar cliente: " + e.getMessage());
            attributes.addFlashAttribute("fail", "Erro ao atualizar cliente!");
            return "redirect:/client/edit/" + client.getId();
        }

        return "redirect:/client/list";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes attributes) {
        try {
            userService.delete(id);
            attributes.addFlashAttribute("sucess", "Cliente excluído com sucesso!");
            return "redirect:/client/list";
        } catch (Exception e) {
            attributes.addFlashAttribute("fail", "Erro ao excluir cliente: O cliente possui propostas cadastradas!");
            return "redirect:/client/list";
        }
    }

    @GetMapping("/{id}/offers")
    public String listClientOffers(@PathVariable Long id, ModelMap model) {
        model.addAttribute("offers", offerService.findAllByClientId(id));
        return "client/offerList";
    }
}

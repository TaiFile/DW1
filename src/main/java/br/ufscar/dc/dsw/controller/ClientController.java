package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.domain.Client;
import br.ufscar.dc.dsw.service.spec.IClientService;
import br.ufscar.dc.dsw.service.spec.IOfferService;
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
    private IOfferService offerService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping("/edit/{id}")
    public String preEdit(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("client", clientService.findById(id));
        return "client/registerUpdate";
    }


    @PostMapping("/edit")
    public String edit(@Valid Client client, String newPassword, BindingResult result, RedirectAttributes attributes, ModelMap model) {
        if (result.hasErrors()) {
            model.addAttribute("client", client);
            return "client/registerUpdate";
        }

        try {
            if (newPassword != null && !newPassword.trim().isEmpty()) {
                client.setPassword(encoder.encode(newPassword));
            } else {
                Client existingClient = clientService.findById(client.getId());
                client.setPassword(existingClient.getPassword());
                System.out.println("Password was not edited");
            }

            clientService.update(client);
        } catch (Exception e) {
            System.err.println("Erro ao atualizar cliente: " + e.getMessage());
            attributes.addFlashAttribute("fail", "Erro ao atualizar cliente!");
            return "redirect:/client/edit/" + client.getId();
        }

        attributes.addFlashAttribute("sucess", "Cliente atualizado com sucesso!");
        return "redirect:/admin/client/list";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes attributes) {
        try {
            clientService.delete(id);
            attributes.addFlashAttribute("sucess", "Cliente excluído com sucesso!");
            return "redirect:/admin/client/list";
        } catch (Exception e) {
            attributes.addFlashAttribute("fail", "Erro ao excluir cliente!");
            return "redirect:/admin/client/list";
        }
    }

    @GetMapping("/{id}/offers")
    public String listClientOffers(@PathVariable Long id, ModelMap model) {
        model.addAttribute("offers", offerService.findAllByClientId(id));
        return "client/offerList";
    }
}

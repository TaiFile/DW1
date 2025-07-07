package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.domain.Client;
import br.ufscar.dc.dsw.domain.Store;
import br.ufscar.dc.dsw.service.impl.ClientService;
import br.ufscar.dc.dsw.service.spec.IStoreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private IStoreService storeService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping("home")
    public String panel() {
        return "admin/home";
    }

    @GetMapping("/client/register")
    public String showRegisterClientForm(Model model) {
        model.addAttribute("client", new Client());
        return "admin/register-client";
    }

    @PostMapping("/client/register")
    public String registerClient(@Valid Client client, BindingResult result, RedirectAttributes attributes, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("client", client);
            return "admin/register-client";
        }

        client.setPassword(encoder.encode(client.getPassword()));

        try {
            clientService.save(client);
        } catch (Exception e) {
            model.addAttribute("client", client);
            model.addAttribute("errorMessage", "Erro interno do servidor. Tente novamente.");
            return "admin/register-client";
        }

        attributes.addFlashAttribute("success", "Cliente cadastrado com sucesso!");
        return "redirect:/admin/home";
    }

    @GetMapping("/store/register")
    public String showRegisterStoreForm(Model model) {
        model.addAttribute("store", new Store());
        return "admin/register-store";
    }

    @PostMapping("/store/register")
    public String registerStore(@Valid Store store, BindingResult result, RedirectAttributes attributes, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("store", store);
            return "admin/register-store";
        }

        store.setPassword(encoder.encode(store.getPassword()));

        try {
            storeService.save(store);
        } catch (Exception e) {
            model.addAttribute("store", store);
            model.addAttribute("errorMessage", "Erro interno do servidor. Tente novamente.");
            return "admin/register-store";
        }

        attributes.addFlashAttribute("success", "Loja cadastrada com sucesso!");
        return "redirect:/admin/home";
    }

    @GetMapping("/client/list")
    public String listClients(Model model) {
        List<Client> clients = clientService.findAll();
        model.addAttribute("clients", clients);
        return "admin/client-list";
    }

    @GetMapping("/store/list")
    public String listStores(Model model) {
        try {
            List<Store> stores = storeService.findAll();
            model.addAttribute("stores", stores);
            return "admin/store-list";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            return "admin/store-list";
        }
    }

}

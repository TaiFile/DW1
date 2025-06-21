package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.domain.Client;
import br.ufscar.dc.dsw.service.impl.ClientService;
import br.ufscar.dc.dsw.service.impl.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private StoreService storeService;

    @GetMapping("home")
    public String panel() {
        return "admin/home";
    }

    @GetMapping("/client/list")
    public String listClients(Model model) {
        List<Client> clients = clientService.findAll();
        model.addAttribute("clients", clients);
        return "admin/client-list";
    }

    @GetMapping("/store/list")
    public String listStores(Model model) {
        model.addAttribute("stores", storeService.findAll());
        return "admin/store-list";
    }
}

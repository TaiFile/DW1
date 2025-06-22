package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.domain.Client;
import br.ufscar.dc.dsw.domain.Store;
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
        List<Client> clients = clientService.findAll(); // ← Mudei para plural
        model.addAttribute("clients", clients);         // ← Mudei para plural
        return "admin/client-list";
    }


    @GetMapping("/store/list")
    public String listStores(Model model) {
        try {
            List<Store> stores = storeService.findAll();
            System.out.println("Número de lojas encontradas: " + (stores != null ? stores.size() : "null"));
            model.addAttribute("stores", stores);
            return "admin/store-list";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            return "admin/store-list";
        }
    }

}

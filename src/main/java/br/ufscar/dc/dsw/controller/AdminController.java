package br.ufscar.dc.dsw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("home")
    public String panel() {
        return "admin/home";
    }

    @GetMapping("/client/list")
    public String listClients() {
        return "admin/client-list";
    }

    @GetMapping("/store/list")
    public String listStores() {
        return "admin/store-list";
    }
}

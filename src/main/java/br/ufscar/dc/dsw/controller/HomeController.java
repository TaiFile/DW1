package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.domain.Client;
import br.ufscar.dc.dsw.domain.Vehicle;
import br.ufscar.dc.dsw.service.impl.VehicleService;
import br.ufscar.dc.dsw.service.spec.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private IClientService clientService;

    @GetMapping("/home")
    public String search(@RequestParam(required = false) String query, ModelMap model, Authentication authentication) {
        List<Vehicle> vehicles;

        if (query != null && !query.trim().isEmpty()) {
            vehicles = vehicleService.findByModelContainingIgnoreCase(query.trim());
            model.addAttribute("searchQuery", query);
        } else {
            vehicles = vehicleService.findAll();
        }

        boolean hasRoleClient = authentication != null &&
                authentication.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_CLIENT"));

        if (hasRoleClient) {
            Client client = clientService.findByEmail(authentication.getName());
            model.addAttribute("clientId", client.getId());
        }

        model.addAttribute("vehicles", vehicles);
        model.addAttribute("query", query);
        return "home";
    }
}

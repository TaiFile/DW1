package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.domain.Vehicle;
import br.ufscar.dc.dsw.service.impl.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/home")
    public String search(@RequestParam(required = false) String query, ModelMap model) {
        List<Vehicle> vehicles;

        if (query != null && !query.trim().isEmpty()) {
            vehicles = vehicleService.findByModelContainingIgnoreCase(query.trim());
            model.addAttribute("searchQuery", query);
        } else {
            vehicles = vehicleService.findAll();
        }

        model.addAttribute("vehicles", vehicles);
        model.addAttribute("query", query);
        return "home";
    }
}

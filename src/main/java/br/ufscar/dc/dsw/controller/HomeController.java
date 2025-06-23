package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.domain.Vehicle;
import br.ufscar.dc.dsw.service.impl.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/home")
    public String home(ModelMap model) {
        List<Vehicle> vehicles = vehicleService.findAll();
        model.addAttribute("vehicles", vehicles);
        return "home";
    }
}

package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.domain.Store;
import br.ufscar.dc.dsw.domain.Vehicle;
import br.ufscar.dc.dsw.service.impl.StoreService;
import br.ufscar.dc.dsw.service.impl.VehicleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private StoreService storeService;

    @GetMapping("/home")
    public String home(ModelMap model) {
        List<Vehicle> vehicles = vehicleService.findAll();
        model.addAttribute("vehicles", vehicles);
        return "home";
    }

    @GetMapping("/store/home")
    public String store(ModelMap model, Authentication authentication) {
        String email = authentication.getName();
        Store store = storeService.findByEmail(email);
        Long storeId = store.getId();

        List<Vehicle> vehicles = vehicleService.findAllByStoreId(storeId);
        model.addAttribute("vehicles", vehicles);
        return "store/home";
    }
}

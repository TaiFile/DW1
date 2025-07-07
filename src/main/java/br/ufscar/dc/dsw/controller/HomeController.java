package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.domain.User;
import br.ufscar.dc.dsw.domain.Vehicle;
import br.ufscar.dc.dsw.domain.enums.UserRoleEnum;
import br.ufscar.dc.dsw.service.impl.VehicleService;
import br.ufscar.dc.dsw.service.spec.IUserService;
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
    private IUserService userService;

    @GetMapping("/home")
    public String search(@RequestParam(required = false) String query, ModelMap model, Authentication authentication) {

        List<Vehicle> vehicles;

        if (query == null) {
            vehicles = vehicleService.findAllAvailable();
        } else {
            vehicles = vehicleService.findAllAvailableAndByModel(query);
            model.addAttribute("searchQuery", query);
        }

        if (authentication != null) {
            User user = userService.findByEmail(authentication.getName());
            model.addAttribute("userId", user.getId());
        }

        model.addAttribute("vehicles", vehicles);
        model.addAttribute("query", query);
        return "home";
    }

    @GetMapping("/store/home")
    public String store(ModelMap model, Authentication authentication) {
        String email = authentication.getName();

        User user = userService.findByEmail(email);
        if (user == null || !user.getRole().equals(UserRoleEnum.STORE)) {
            return "redirect:/home";
        }

        List<Vehicle> vehicles = vehicleService.findAllByStoreId(user.getId());
        model.addAttribute("vehicles", vehicles);
        return "store/home";
    }
}

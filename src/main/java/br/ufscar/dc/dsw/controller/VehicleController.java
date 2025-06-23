package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.domain.Store;
import br.ufscar.dc.dsw.domain.Vehicle;
import br.ufscar.dc.dsw.service.spec.IStoreService;
import br.ufscar.dc.dsw.service.spec.IVehicleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/vehicle")
public class VehicleController {
    @Autowired
    private IVehicleService vehicleService;

    @Autowired
    private IStoreService storeService;

    @GetMapping("/register")
    public String register(Vehicle vehicle) {
        return "vehicle/register";
    }

    @GetMapping("/list")
    public String list(ModelMap model) {
        model.addAttribute("vehicles", vehicleService.findAll());
        return "vehicle/list";
    }

    @PostMapping("/save")
    public String save(@Valid Vehicle vehicle, BindingResult result, RedirectAttributes attributes) {
        if(result.hasErrors()) {
            return "vehicle/register";
        }

        vehicleService.save(vehicle);
        attributes.addFlashAttribute("sucess", "vehicle.create.success");
        return "redirect:/vehicle/list";
    }

    @GetMapping("/edit/{id}")
    public String preEdit(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("vehicle", vehicleService.findById(id));
        return "store/home";
    }

    @PostMapping("/edit")
    public String edit(@Valid Vehicle vehicle, BindingResult result, RedirectAttributes attributes) {
        if(result.hasErrors()) {
            return "store/home";
        }

        vehicleService.update(vehicle);
        attributes.addFlashAttribute("sucess", "vehicle.edit.success");
        return "redirect:/store/home";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes attributes) {
        vehicleService.delete(id);
        attributes.addFlashAttribute("sucess", "vehicle.delete.success");
        return "redirect:/store/home";
    }

    @ModelAttribute("stores")
    public List<Store> listStore() {
        return storeService.findAll();
    }
}

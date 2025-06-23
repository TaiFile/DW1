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

import java.util.Date;
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
    public String editForm(@PathVariable("id") Long id, ModelMap model) {
        Vehicle vehicle = vehicleService.findById(id);
        model.addAttribute("vehicle", vehicle);
        return "vehicle/registerUpdate";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Vehicle vehicle, RedirectAttributes attributes) {
        try {
            vehicleService.save(vehicle);
            attributes.addFlashAttribute("success", "Veículo atualizado com sucesso!");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Erro ao atualizar veículo!");
        }
        return "redirect:/store/home";
    }



    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes attributes) {
        try {
            vehicleService.delete(id);
            attributes.addFlashAttribute("success", "Veículo excluído com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao deletar veículo: " + e.getMessage());
            attributes.addFlashAttribute("error", "Erro ao excluir veículo!");
        }

        return "redirect:/store/home";
    }


    @ModelAttribute("stores")
    public List<Store> listStore() {
        return storeService.findAll();
    }
}

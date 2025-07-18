package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.domain.Store;
import br.ufscar.dc.dsw.domain.Vehicle;
import br.ufscar.dc.dsw.exceptions.ResourceNotFoundException;
import br.ufscar.dc.dsw.service.spec.IStoreService;
import br.ufscar.dc.dsw.service.spec.IVehicleService;
import br.ufscar.dc.dsw.storage.IStorageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/vehicle")
public class VehicleController {
    @Autowired
    private IVehicleService vehicleService;

    @Autowired
    private IStoreService storeService;

    @Autowired
    private IStorageService storageService;

    @GetMapping("/register")
    public String register(Vehicle vehicle, ModelMap model) {
        model.addAttribute("vehicle", new Vehicle());
        return "vehicle/register";
    }

    @GetMapping("/list")
    public String list(ModelMap model) {
        model.addAttribute("vehicles", vehicleService.findAllAvailable());
        return "vehicle/list";
    }

    @PostMapping("/save")
    public String save(@Valid Vehicle vehicle,
                       BindingResult result, // Precisa estar posicionado logo após a class com @Valid
                       @RequestParam(required = false) MultipartFile[] imageFiles,
                       Principal principal, RedirectAttributes attributes, ModelMap model) {
        if (result.hasErrors()) {
            return "vehicle/register";
        }

        try {
            List<String> finalImages = new ArrayList<>();
            for (MultipartFile file : imageFiles) {
                String fileName = storageService.store(file);
                finalImages.add(fileName);
            }

            Store store = storeService.findByEmail(principal.getName())
                    .orElseThrow(() -> new ResourceNotFoundException("Store not found"));
            vehicle.setStore(store);
            vehicle.setImages(finalImages);
            vehicleService.save(vehicle);
            attributes.addFlashAttribute("sucess", "Vehicle created successfully");

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("fail", "Error creating a vehicle!");
            return "vehicle/register";
        }

        return "redirect:/store/home";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Long id, ModelMap model) {
        Vehicle vehicle = vehicleService.findById(id);
        model.addAttribute("vehicle", vehicle);
        return "vehicle/update";
    }

    @PostMapping("/edit")
    public String edit(@Valid Vehicle vehicle,
                       BindingResult result,
                       @RequestParam(required = false) String[] existingImages,
                       @RequestParam(required = false) MultipartFile[] newImages,
                       RedirectAttributes attributes,
                       ModelMap model) {
        if (result.hasErrors()) {
            model.addAttribute("vehicle", vehicle);
            return "vehicle/update";
        }

        try {
            List<String> finalImages = new ArrayList<>();
            if (existingImages != null) {
                finalImages.addAll(List.of(existingImages));
            }

            if (newImages != null) {
                for (MultipartFile file : newImages) {
                    if (!file.isEmpty() && file.getContentType() != null) {
                        String fileName = storageService.store(file);
                        if (fileName != null) {
                            finalImages.add(fileName);
                        }
                    }
                }
            }

            Vehicle vehicleToUpdate = vehicleService.findById(vehicle.getId());
            if (vehicleToUpdate != null && vehicleToUpdate.getImages() != null) {
                for (String imageUrl : vehicleToUpdate.getImages()) {
                    if (!finalImages.contains(imageUrl)) {
                        storageService.delete(imageUrl);
                    }
                }
            }

            vehicle.setImages(finalImages);
            vehicleService.save(vehicle);
            attributes.addFlashAttribute("sucess", "Veículo atualizado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("fail", "Erro ao atualizar veículo!");
            return "vehicle/update";
        }
        return "redirect:/store/home";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes attributes, ModelMap model) {
        try {
            Vehicle vehicle = vehicleService.findById(id);
            if (vehicle != null && vehicle.getImages() != null) {
                for (String imageUrl : vehicle.getImages()) {
                    storageService.delete(imageUrl);
                }
            }

            vehicleService.delete(id);
            attributes.addFlashAttribute("success", "Veículo excluído com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao deletar veículo: " + e.getMessage());
            attributes.addFlashAttribute("error", "Erro ao excluir veículo! O veículo possui propostas cadastradas!");
            return "redirect:/store/home";
        }

        return "redirect:/store/home";
    }

    @ModelAttribute("stores")
    public List<Store> listStore() {
        return storeService.findAll();
    }
}

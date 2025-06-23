package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.domain.Store;
import br.ufscar.dc.dsw.service.spec.IStoreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/store")
public class StoreController {
    @Autowired
    private IStoreService storeService;

    @GetMapping("/register")
    public String register(Store store) {
        return "/store/register";
    }

    @GetMapping("/list")
    public String list(ModelMap model) {
        model.addAttribute("stores", storeService.findAll());
        return "store/list";
    }

    @PostMapping("/save")
    public String save(@Valid Store store, BindingResult result, RedirectAttributes attributes) {
        if(result.hasErrors()) {
            return "store/register";
        }

        storeService.save(store);
        attributes.addFlashAttribute("sucess", "store.create.success");
        return "redirect:/store/list";
    }

    @GetMapping("/edit/{id}")
    public String preEdit(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("store", storeService.findById(id));
        return "store/registerUpdate";
    }



    @PostMapping("/edit")
    public String edit(@Valid Store store, BindingResult result, RedirectAttributes attributes) {
        if(result.hasErrors()) {
            return "store/registerUpdate";
        }

        try {
            storeService.update(store);
            attributes.addFlashAttribute("sucess", "Loja atualizada com sucesso!");
            return "redirect:/admin/store/list";
        } catch (Exception e) {
            attributes.addFlashAttribute("fail", "Erro ao atualizar loja!");
            return "store/registerUpdate";
        }
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        boolean hasVehicles = storeService.storeHaveVehicles(id);

        if(hasVehicles) {
            redirectAttributes.addFlashAttribute("fail", "store.delete.fail");
        } else {
            storeService.delete(id);
            redirectAttributes.addFlashAttribute("success", "store.delete.success");
        }

        return "redirect:/admin/store/list";
    }
}

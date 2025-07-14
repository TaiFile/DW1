package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.domain.Store;
import br.ufscar.dc.dsw.service.spec.IOfferService;
import br.ufscar.dc.dsw.service.spec.IStoreService;
import br.ufscar.dc.dsw.service.spec.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IStoreService storeService;

    @Autowired
    private IOfferService offerService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping("/register")
    public String showRegisterStoreForm(Model model) {
        model.addAttribute("store", new Store());
        return "store/register";
    }

    @PostMapping("/register")
    public String registerStore(@Valid Store store, BindingResult result, RedirectAttributes attributes, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("store", store);
            return "store/register";
        }

        store.setPassword(encoder.encode(store.getPassword()));

        try {
            userService.save(store);
        } catch (Exception e) {
            model.addAttribute("store", store);
            model.addAttribute("errorMessage", "Erro interno do servidor. Tente novamente.");
            return "store/register";
        }

        attributes.addFlashAttribute("success", "Loja cadastrada com sucesso!");
        return "redirect:/admin/home";
    }

    @GetMapping("/list")
    public String listStores(Model model) {
        try {
            List<Store> stores = storeService.findAll();
            model.addAttribute("stores", stores);
            return "store/list";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            return "store/list";
        }
    }

    @GetMapping("/edit/{id}")
    public String preEdit(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("store", userService.findById(id));
        return "store/update";
    }

    @PostMapping("/edit")
    public String edit(@Valid Store store, BindingResult result, RedirectAttributes attributes, ModelMap model) {
        if (result.hasErrors()) {
            return "store/update";
        }

        try {
            if (store.getPassword() != null && !store.getPassword().trim().isEmpty()) {
                store.setPassword(encoder.encode(store.getPassword()));
                System.out.println("Password was edited" + store.getPassword());
            }
            userService.update(store);
            attributes.addFlashAttribute("sucess", "Loja atualizada com sucesso!");
            return "redirect:/store/list";
        } catch (Exception e) {
            model.addAttribute("fail", "Erro ao atualizar loja!");
            return "store/update";
        }
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        boolean hasVehicles = storeService.storeHasVehicles(id);

        if (hasVehicles) {
            redirectAttributes.addFlashAttribute("fail", "Falha ao excluir loja: loja possui veículos cadastrados.");
        } else {
            userService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Loja excluída com sucesso!");
        }

        return "redirect:/store/list";
    }

    @GetMapping("/offers")
    public String listStoreOffers(Principal principal, ModelMap model) {
        model.addAttribute("offers", offerService.findAllByStoreEmail(principal.getName()));
        return "store/offerList";
    }
}

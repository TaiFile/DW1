package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.domain.Offer;
import br.ufscar.dc.dsw.service.spec.IOfferService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/offer")
public class OfferController {
    @Autowired
    private IOfferService offerService;

    @GetMapping("/register")
    public String register(Offer offer) {
        return "offer/register";
    }

    @GetMapping("/list")
    public String list(ModelMap model) {
        model.addAttribute("offer", offerService.findAll());
        return "offer/list";
    }

    @PostMapping("/save")
    public String save(@Valid Offer offer, BindingResult result, RedirectAttributes attributes) {
        if(result.hasErrors()) {
            return "offer/resgister";
        }

        offerService.save(offer);
        attributes.addFlashAttribute("sucess", "offer.create.success");
        return "redirect:/offer/list";
    }

    @GetMapping("/edit/{id}")
    public String preEdit(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("offer", offerService.findById(id));
        return "offer/register";
    }

    @PostMapping("/edit")
    public String edit(@Valid Offer offer, BindingResult result, RedirectAttributes attributes) {
        if(result.hasErrors()) {
            return "offer/resgister";
        }

        offerService.update(offer);
        attributes.addFlashAttribute("sucess", "offer.edit.success");
        return "redirect:/offer/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes attributes) {
        offerService.delete(id);
        attributes.addFlashAttribute("sucess", "offer.delete.success");
        return "redirect:/offer/list";
    }
}

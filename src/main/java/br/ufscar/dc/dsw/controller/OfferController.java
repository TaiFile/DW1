package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.domain.Offer;
import br.ufscar.dc.dsw.domain.Vehicle;
import br.ufscar.dc.dsw.service.spec.IOfferService;
import br.ufscar.dc.dsw.service.spec.IVehicleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class OfferController {

    @Autowired
    private IOfferService offerService;

    @Autowired
    private IVehicleService vehicleService;

    @GetMapping("/vehicle/{id}/offer/register")
    public String register(@PathVariable Long id, ModelMap model) {
        model.addAttribute("vehicle", vehicleService.findById(id));
        return "vehicle/offer";
    }

    @PostMapping("/vehicle/{vehicleId}/offer/save")
    public String save(@PathVariable Long vehicleId, @Valid Offer offer, BindingResult result, RedirectAttributes attributes, ModelMap model) {
        Vehicle vehicle = vehicleService.findById(vehicleId);

        if (result.hasErrors()) {
            model.addAttribute("vehicle", vehicle);
            model.addAttribute("fail", "Preencha todos os campos obrigatórios!");
            return "vehicle/offer";
        }

        offer.setVehicle(vehicle);

        try {
            offerService.save(offer);
            attributes.addFlashAttribute("sucess", "Proposta enviada com sucesso!");
            return "redirect:/home";
        } catch (Exception e) {
            model.addAttribute("vehicle", vehicle);
            model.addAttribute("fail", "Erro ao enviar proposta. Tente novamente!");
            return "vehicle/offer";
        }
    }

    @GetMapping("/client/{clientId}/offers")
    public String listByClient(@PathVariable Long clientId, ModelMap model) {
        model.addAttribute("offer", offerService.findAllByClientId(clientId));
        return "client/offerList";
    }

    @GetMapping("/store/{storeId}/offers")
    public String listByStore(@PathVariable Long storeId, ModelMap model) {
        model.addAttribute("offer", offerService.findAllByStoreId(storeId));
        return "store/offerList";
    }

    // Apenas a loja deve editar uma proposta (mudar o status)
    @GetMapping("/edit/{id}")
    public String preEdit(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("offer", offerService.findById(id));
        return "offer/register";
    }

    @PostMapping("/edit")
    public String edit(@Valid Offer offer, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
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
